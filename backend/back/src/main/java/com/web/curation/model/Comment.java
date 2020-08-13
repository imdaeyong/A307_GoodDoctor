package com.web.curation.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="\"comment\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private int userId;
    
    private int feedId;
	private String content;
	@Column(updatable = false)
    private LocalDateTime createDate;

    private String nickname;
    
    @Column(insertable = false)
    private String imageUrl;

}
