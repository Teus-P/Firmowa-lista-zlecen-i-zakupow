package pl.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "tabela", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
public class Tabela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="tabelacol")
    private String tabelaCol;


    public Tabela() {
    }


    public Tabela(String tabelaCol) {
        this.tabelaCol = tabelaCol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTabelaCol() {
        return tabelaCol;
    }

    public void setTabelaCol(String tabelaCol) {
        this.tabelaCol = tabelaCol;
    }
}
