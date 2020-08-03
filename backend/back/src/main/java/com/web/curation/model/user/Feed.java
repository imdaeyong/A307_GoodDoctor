
package com.web.curation.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@Entity
@Table(name="\"feed\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;	

    @ManyToOne
	@JoinColumn(name ="user_id")
    private User user;
    
    @ManyToOne
	@JoinColumn(name ="hospital_id")
    private Hospital hospital;
    
    private String content;
    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;
    @Column(insertable = false)
    private LocalDateTime updateDate;
    private int likes;
    private Boolean isNew;
    private boolean isClick;
}
