package com.baizhi.controller;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import com.baizhi.service.*;
import com.baizhi.util.MessageUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WorldMapDao worldMapDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumDao albumDao;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("login")
    public Map login(User user) {
        Map map = userService.findOne(user);
        return map;
    }

    @RequestMapping("sendCode")
    public Map sendCode(String phone) {
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,4);
        MessageUtil.sendMessage(phone, code);
        stringRedisTemplate.opsForValue().set(phone,code);
        stringRedisTemplate.expire(phone,60 , TimeUnit.SECONDS);
        Map map = new HashMap();
        map.put("status", 200);
        map.put("option", code);
        map.put("message", "验证码发送成功");
        return map;
    }
    @RequestMapping("careGuru")
    public Map careGuru(String uid,String courseId) {
        Map map = new HashMap();
        Set<String> set = new HashSet();
        Boolean aBoolean = stringRedisTemplate.opsForSet().isMember(courseId, uid);
        if (aBoolean) {
            map.put("status", -200);
            map.put("message", "该上师已关注");
        } else {
            Boolean key = stringRedisTemplate.hasKey(courseId);
            if (key) {
                set = stringRedisTemplate.opsForSet().members(courseId);
                set.add(uid);
                stringRedisTemplate.opsForSet().add(courseId, uid);
                stringRedisTemplate.opsForSet().add(uid, courseId);
            } else {
                set.add(uid);
                stringRedisTemplate.opsForSet().add(courseId, uid);
                stringRedisTemplate.opsForSet().add(uid, courseId);
            }
            map.put("status", 200);
            map.put("message", "关注成功");
            map.put("list", set);
        }
        return map;
    }
    @RequestMapping("cmfzFriend")
    public Map cmfzFriend(String uid) {
        Map map = new HashMap();
        Set<String> set = new HashSet();
        Set<String> keys = stringRedisTemplate.opsForSet().members(uid);
        if (keys.isEmpty()) {
            System.out.println("没有在redis中");
            int count = userDao.selectCount(new User());
            Integer total = count % 5 == 0 ? count / 5 : count / 5 + 1;
            Map map1 = userService.findByCurrentPage((int) (Math.random() * (total-1) + 1), 5);
            map.put("status", 200);
            map.put("option", map1.get("rows"));
        } else {
            System.out.println("在redis中:"+keys);
            Set<String> friends = stringRedisTemplate.opsForSet().intersect(keys);
            List list = new ArrayList();
            for (String friend : friends) {
                list.add(friend);
            }
            List list1 = userDao.selectByIdList(list);
            map.put("status", 200);
            map.put("option", list1);
        }
        return map;
    }

    @RequestMapping("regist")
    public Map regist(User user, String code) {
        Map map = new HashMap();
        if (code.equalsIgnoreCase("hxl")) {
            if (userService.findOne(user).get("user") == null) {
                Map save = userService.save(user);
                map.put("status", 200);
                map.put("uid", save.get("userId"));
            } else {
                map.put("status", -200);
                map.put("message", "该手机号已被注册");
            }
        } else {
            map.put("status", -200);
            map.put("message", "验证码错误");
        }
        return map;
    }

    @RequestMapping("userMsg")
    public Map userMsg(User user) {
        return userService.userMsg(user);
    }

    @RequestMapping("home")
    public Map home(String uid, String type, String sub_type) {
        Map all = new HashMap();
        Banner banner = new Banner();
        banner.setStatus("1");
        Album album = new Album();
        album.setStatus("1");
        Article article = new Article();
        article.setStatus("1");
        int count = bannerDao.selectCount(banner);
        int count1 = albumDao.selectCount(album);
        int count2 = articleDao.selectCount(article);
        Integer total1 = count % 5 == 0 ? count / 5 : count / 5 + 1;
        Integer total2 = count1 % 6 == 0 ? count1 / 6 : count1 / 6 + 1;
        Integer total3 = count2 % 6 == 0 ? count2 / 6 : count2 / 6 + 1;
        System.out.println((int) (Math.random() * (total1-1) + 1)+"----"+(int) (Math.random() * (total2-1) + 1)+"------"+(int) (Math.random() * (total1-1) + 1));
        Map map = bannerService.findByCurrentPage((int) (Math.random() * (total1-1) + 1), 5);
        Map map1 = albumService.findByCurrentPage((int) (Math.random() * (total2-1) + 1), 6);
        Map map2 = articleService.findByCurrentPage((int) (Math.random() * (total3-1) + 1), 6);
        all.put("status", 200);
        all.put("head", map.get("rows"));
        all.put("albums", map1.get("rows"));
        all.put("articles", map2.get("rows"));
        return all;
    }





    @RequestMapping("showAllBanners")
    public Map showAllBanners(String searchField, String searchString, String searchOper, Boolean _search, Integer page, Integer rows) {
        if (_search) {
            return userService.findAllSearch(searchField, searchString, searchOper, page, rows);
        } else {
            return userService.findByCurrentPage(page, rows);
        }
    }

    @RequestMapping("change")
    public Map change(String oper, User user, String[] id) {
        if ("add".equals(oper)) {
            user.setRigestDate(new Date());
            return userService.save(user);
        } else if ("edit".equals(oper)) {
            user.setLastLogin(new Date());
            return userService.update(user);
        } else {
            List<String> list = new ArrayList<>();
            for (String s : id) {
                list.add(s);
            }
            System.out.println();
            return userService.deleteList(list);
        }
    }

    @RequestMapping("upload")
    public void upload(MultipartFile photo, String userId, HttpServletRequest request) throws IOException {
        User user = new User();
        if (photo.getSize() > 0) {
            //相对路径获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/files");
            String fileNamePrefix = UUID.randomUUID().toString().replace("-", "")
                    + new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
            String fileNameSuffix = FilenameUtils.getExtension(photo.getOriginalFilename());
            String fileName = fileNamePrefix + "." + fileNameSuffix;
            //创建新的日期目录
            String dateDirString = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File dateDir = new File(realPath, dateDirString);
            if (!dateDir.exists()) {
                dateDir.mkdirs();
            }
            //生成上传文件
            photo.transferTo(new File(dateDir, fileName));
            //获取网络路径
            String http = request.getScheme();
            String localHost = InetAddress.getLocalHost().toString();
            int serverPort = request.getServerPort();
            String contextPath = request.getContextPath();
            String uri = http + "://" + localHost.split("/")[1] + ":" + serverPort + contextPath + "/files/" + dateDirString + "/" + fileName;
            System.out.println("路径为" + uri);
            user.setPhoto(uri);
            user.setId(userId);
            userDao.updateByPrimaryKeySelective(user);
        }

    }

    @RequestMapping("findPeopleNum")
    public Map<String, List<Integer>> findPeopleNum() {
        Map map = new HashMap();
        List<Integer> list1 = new ArrayList();
        list1.add(userDao.findCountManDay(1));
        list1.add(userDao.findCountManDay(7));
        list1.add(userDao.findCountManDay(30));
        list1.add(userDao.findCountManDay(365));
        List<Integer> list2 = new ArrayList();
        list2.add(userDao.findCountWomenDay(1));
        list2.add(userDao.findCountWomenDay(7));
        list2.add(userDao.findCountWomenDay(30));
        list2.add(userDao.findCountWomenDay(365));
        map.put("man", list1);
        map.put("women", list2);
        return map;
    }

    @RequestMapping("findMap")
    public List findMap() {
        List<WorldMap> list = worldMapDao.findAll();
        return list;
    }
}

