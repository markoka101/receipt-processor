package mark.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import mark.demo.pojo.Item;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Receipt {

    @Id
    @UuidGenerator
    @Column(name = "id", updatable = false,  columnDefinition = "VARCHAR(36)")
    private String id;

    @Column
    @NotNull
    private String retailer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column
    @NotNull
    private LocalDate purchaseDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Column
    @NotNull
    private LocalTime purchaseTime;

    @ElementCollection
    @Size(min = 1)
    @Column
    private Set<Item> items;

    @NotNull
    @Column
    private double total;
}
