package com.converter.task.models;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "exchange", schema = "public")
public class Exchange {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "currency1")
    private String currency1;
    @Column(name = "currency2")
    private String currency2;
    @Column(name = "rate1")
    private Double rate1;
    @Column(name = "rate2")
    private Double rate2;
    @Column(name = "date")
    private Date date;
}
