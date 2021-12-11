package web.learning.system.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "task")
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
public class Task {

    public Task(String title, String description, String map, LocalDateTime date, Boolean isPublished, User author) {
        this.title = title;
        this.description = description;
        this.map = map;
        this.date = date;
        this.isPublished = isPublished;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "map")
    private String map;

    @Column(name = "created_date")
    private LocalDateTime date;

    @Column(name = "is_published")
    private Boolean isPublished;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Set<Solution> solutions;
}
