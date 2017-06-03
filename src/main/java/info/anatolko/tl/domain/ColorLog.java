package info.anatolko.tl.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "color_log")
public class ColorLog implements Serializable{

    private static final long serialVersionUID = 5402151523535598250L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @NotNull
    @Column(name = "date")
    private LocalDateTime date;

    public ColorLog() {
    }

    public ColorLog(Color color) {
        this.color = color;
        this.date = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
