package io.microServices.tickets.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment extends AbstractPersistable<Integer> {

    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private String userId;

    @ManyToOne
    private Ticket ticket;
}