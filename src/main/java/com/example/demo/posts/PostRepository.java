package com.example.demo.posts;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.database.entities.Post;
import com.example.demo.database.entities.SubCategory;
import com.example.demo.database.entities.User;
import com.example.demo.database.entities.Address;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Post findByTitle(String title);
    List<Post> findAllByUser(User user);
    Page<Post>findAllByUser( User user,Specification<Post> spec,Pageable pageable);
    Page<Post>findAllByUser( User user,Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);
    Page<Post> findAllByUserAndIsAcceptTrue(User user,Pageable pageable);
    Page<Post> findAllByUserAndIsAcceptFalse(User user,Pageable pageable);
    Page<Post> findAllByIsAcceptTrue(Pageable pageable);
    Page<Post> findAllByIsAcceptFalse(Pageable pageable);
    List<Post> findAllByCreatedAtBetween(Date start,Date end);
    List<Post> findBySubCategory(SubCategory subcategory);
    @Query( value = "SELECT * FROM post where title like %?1% and id in ?2 and is_accept = 1", nativeQuery= true)
    List<Post> findAllByTitle(String title, List<UUID> ids);
    //List<Post> findAllByAddress(User user);
    List<Post> findByAddress(Address address);
    @Query( value = "SELECT post.* FROM `post`, `category`, `sub_category` where post.address_id in (SELECT address.id FROM address, city,ward,  distric where city.id = distric.city_id and address.ward_id = ward.id and ward.city_id = city.id and ward.distric_id = distric.id and city.id = ?1 )  and post.sub_category_id = sub_category.id and sub_category.category_id = category.id ;", nativeQuery= true)
    List<Post> findAllByCity(int city);
    @Query( value = "SELECT post.* FROM post, category, sub_category where post.address_id in (SELECT address.id FROM address, city,ward,  distric where city.id = distric.city_id and address.ward_id = ward.id and ward.city_id = city.id and ward.distric_id = distric.id and distric.id = ?1 )  and post.sub_category_id = sub_category.id and sub_category.category_id = category.id ;", nativeQuery= true)
    List<Post> findAllByDistrict(int district);
    @Query( value = "SELECT post.* FROM `post`, `category`, `sub_category` where post.address_id in (SELECT address.id FROM address, city,ward,  distric where city.id = distric.city_id and address.ward_id = ward.id and ward.city_id = city.id and ward.distric_id = distric.id and ward.id = ?1 )  and post.sub_category_id = sub_category.id and sub_category.category_id = category.id ; ", nativeQuery= true)
    List<Post> findAllByward(int ward);
    @Query( value = "SELECT post.* FROM `post`, `category`, `sub_category` where post.sub_category_id = sub_category.id and sub_category.category_id = category.id and category.id = ?1  ;", nativeQuery= true)
    List<Post> findAllByCategory(UUID id);
    @Query( value = "SELECT * FROM post where title like %?1% and is_accept = 1", nativeQuery= true)
    List<Post> findAllByTitle(String title);
    @Query( value = "SELECT * FROM post where is_accept = 1 ORDER BY created_at DESC LIMIT ?1", nativeQuery= true)
    List<Post> findTop5(int limit);
    @Query(value = "DELETE FROM post WHERE id= ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void delete(UUID id);

}
