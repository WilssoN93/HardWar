package com.Hardwar.Persistence.Entitys;

import javax.persistence.*;

@Entity
@Table(name = "`specification`")
public class Specification {

    String value;
    String key;
    Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(name = "`key`")
    public String getKey() {
        return key;
    }

    @Column(name = "`value`")
    public String getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "value='" + value + '\'' +
                ", key='" + key +
                '}';
    }
}
