package io.mountblue.BlogApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "author")
    @NotEmpty(message = "author name should not be empty")
    String author;

    @NotEmpty(message = "title should not be empty")
    @Column(name = "title")
    String title;

    @NotEmpty(message = "excerpt should not be empty")
    @Column(name = "excerpt",length = 1000)
    String excerpt;

    @NotEmpty(message = "content should not be empty")
    @Column(name = "content",columnDefinition = "TEXT")
    String content;

    @CreationTimestamp
    @Column(name = "published_at")
    Date publishedAt;

    @Column(name = "is_published",columnDefinition = "BOOLEAN DEFAULT true")
    Boolean isPublished;

    @Column(name="created_at")
    @CreationTimestamp
    Date createdAt;
    @Column(name = "updated_at")
    @UpdateTimestamp
    Date updatedAt;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    List<Comment> commentList = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    List<Tag> tags = new ArrayList<>();

}