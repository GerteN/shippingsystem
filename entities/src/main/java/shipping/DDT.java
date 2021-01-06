package shipping;

import javax.persistence.*;

@Entity
public class DDT {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    //@SequenceGenerator(name = "ddt_seq")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public DDT setId(Integer id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "DDT{" +
                "id=" + id +
                '}';
    }

}
