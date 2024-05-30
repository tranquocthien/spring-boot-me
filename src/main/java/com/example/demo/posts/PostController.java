package com.example.demo.posts;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.group4.Heper.FileStorageService;
import com.example.demo.address.AddressService;
import com.group4.Service.CityService;
import com.group4.Service.PostPhotoService;
import com.group4.Service.PostService;
import com.group4.Service.SubCategoryService;
import com.group4.Service.UserService;
import com.example.demo.database.entities.Address;
import com.example.demo.database.entities.Post;
import com.group4.entity.PostPhoto;
import com.example.demo.database.entities.User;
import com.example.demo.database.entities.Ward;

@Controller
@RequestMapping(value = "/post")
public class PostController extends AbtractController {

    PostService postService;

    AddressService addressService;
    PostPhotoService postPhotoService;

    SubCategoryService subCategoryService;
    UserService userService;
    FileStorageService fileStorageService;
    CityService cityService;

    @GetMapping(value = "/new")
    public String createNew(Model model, Authentication authentication, HttpSession session) {
        session.setAttribute("title", "Them bai dang moi");
        User user = this.getCurentUser(authentication);
        System.out.println(user.getEmail());
        if (user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("CITYS", cityService.findAll());
        model.addAttribute("post", new Post());
        model.addAttribute("subcate", subCategoryService.findAll());
        return "post/form";
    }

    @GetMapping(value = "/{id}")
    public String createNew(@PathVariable("id") UUID id, Model model, Authentication uthentication,
                            HttpSession session) {
        session.setAttribute("title", "Cap nhat bai dang");
        User user = this.getCurentUser(uthentication);
        Post post = postService.findById(id).get();
        System.out.println(post.getPhotos().size());
        List<PostPhoto> photos = postPhotoService.findByPost(post);
        if (user.getPosts().indexOf(post) < 0) {
            return "redirect:/error";
        }
        model.addAttribute("CITYS", cityService.findAll());
        model.addAttribute("subcate", subCategoryService.findAll());
        model.addAttribute("IMAGES", photos);
        System.out.println(photos.size());
        model.addAttribute("post", post);
        System.out.println("oke");
        return "post/updatepost";
    }

    @PostMapping(value = "update")
    public String updatePost(@ModelAttribute("post") Post post, @RequestParam("images") MultipartFile[] file,
                             Model model, Authentication uthentication, @RequestParam(value = "imageIds", required = false) UUID[] ids,
                             @RequestParam int ward_id, @RequestParam String address_string) throws IOException {
        Address address = null;
        User user = this.getCurentUser(uthentication);
        post.setUser(user);
        HashSet<UUID> photoIDs = new HashSet<>();
        HashSet<UUID> idNotDel = new HashSet<>();;
        List<PostPhoto> photoList = postPhotoService.findByPost(post);
        for (int i = 0; i < photoList.size(); i++) {
            photoIDs.add(photoList.get(i).getId());
        }
        HashSet<UUID> differentSet = (HashSet<UUID>) photoIDs.clone();
        if (ids != null) {
            idNotDel = new HashSet<>(Arrays.asList(ids));
            HashSet<UUID> idNotDelCopy = (HashSet<UUID>) idNotDel.clone();
            // tìm phần tử khác nhau giữa diffentSet với idNotDel
            differentSet.removeAll(idNotDel);

            // tìm phần tử khác nhau giữa idNotDelCopy và photoIDs
            idNotDelCopy.removeAll(photoIDs);
            differentSet.addAll(idNotDelCopy);
        }

        System.out.println(differentSet);

        if (ward_id == post.getAddress().getWard().getId()) {
            address = post.getAddress();
            address.setAddress(address_string);
        } else {
            address = new Address();
            Ward ward = new Ward();
            ward.setId(ward_id);
            address.setAddress(address_string);
            address.setWard(ward);
        }

        Address add = addressService.save(address);
        post.setAddress(add);
        if (file.length == 0 && differentSet.size() == 0) {

        }  else {
            if (differentSet.size() >= file.length) {//
                int i = 0;
                for (UUID uuid : differentSet) {
                    if (i <= file.length - 1) { //
                        System.out.println(i <= file.length - 1);
                        PostPhoto photo = postPhotoService.findById(uuid).get();
                        photo.setName(fileStorageService.storeFile(file[i]));
                        photo.setPost(post);
                        postPhotoService.save(photo);
                    } else {
                        postPhotoService.delete(uuid);
                    }
                    i++;
                }
            }
            if (differentSet.size() < file.length) {// so file tahy doi < so file tai len => vua update vua tao moi file
                UUID[] myArr = new UUID[0];
                if (differentSet !=null && differentSet.size() >0) {
                    myArr = (UUID[]) differentSet.toArray();
                }
                for (int i = 0; i < file.length; i++) {
                    if (i <= myArr.length - 1) {
                        PostPhoto photo = postPhotoService.findById(myArr[i]).get();
                        photo.setName(fileStorageService.storeFile(file[i]));
                        photo.setPost(post);
                        postPhotoService.save(photo);
                    } else {
                        PostPhoto photo = new PostPhoto();
                        photo.setName(fileStorageService.storeFile(file[i]));
                        photo.setPost(post);
                        postPhotoService.save(photo);
                    }
                }
            }
        }
        if (user == null) {
            return "redirect:/auth/login";
        }
        Post postSave = postService.save(post);
        if (postSave == null) {
            return "post/updatepost";
        }
        return "redirect:/user/manager";
    }

    @PostMapping(value = "/new/upload")
    public String createNew(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult,
                            @RequestParam("images") MultipartFile[] file, @RequestParam int ward_id,
                            @RequestParam String address_string, ModelMap model, Authentication uthentication) throws IOException {
        if (bindingResult.hasErrors()) {
            return "post/form";
        }
        if (file.length > 6 || file.length == 0) {
            model.addAttribute("ERR_FILE", "Phải chọn ít nhất 1 hình ảnh và không quá 6 ảnh !");
            return "post/form";
        }
        Ward ward = new Ward();
        ward.setId(ward_id);
        Address add = new Address();
        add.setAddress(address_string);

        add.setWard(ward);
        Address address = addressService.save(add);

        post.setCreatedAt(new Timestamp(new Date().getTime()));
        post.setAccept(false);

        post.setAddress(address);

        post.setUser(this.getCurentUser(uthentication));
        /* post.setPhotos(photos); */

        Post postSave = postService.save(post);
        if (postSave == null) {

        }
        for (int i = 0; i < file.length; i++) {
            PostPhoto p = new PostPhoto();
            p.setPost(postSave);
            p.setName(fileStorageService.storeFile(file[i]));
            postPhotoService.save(p);
        }

        return "redirect:/user/manager";
    }

    @GetMapping(value = "/view/{id}")
    public String viewPost(@PathVariable UUID id, ModelMap modelMap, HttpSession session) {
        session.setAttribute("title", "Chi tiet bai dang");
        Post post = postService.findById(id).get();
        modelMap.addAttribute("POST", post);
        List<PostPhoto> photos = postPhotoService.findByPost(post);
        modelMap.addAttribute("PHOTOS", photos);
        return "post/view";

    }
    @GetMapping(value = "/delete/{id}")
    public String deletePost(@PathVariable UUID id,  Authentication uthentication) {
        User user = this.getCurentUser(uthentication);
        Post post = postService.findById(id).get();
        if (post == null) {
            return "redirect:/error";
        }
        if (!user.getPosts().contains(post)) {
            return "redirect:/error";
        }
        postService.deleteById(id);
        return "redirect:/user/manager";


    }
}
