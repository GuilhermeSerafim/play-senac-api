package com.playsenac.api.dto;

import com.playsenac.api.entities.UsuarioEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome pode ter no máximo {max} caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 100, message = "Senha deve ter entre {min} e {max} caracteres")
    private String senha;

    private String telefone;

    private int fk_role;

    public UsuarioDTO(String nome, String email, String senha, String telefone, int fk_role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.fk_role = fk_role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setFk_role(int fk_role) {
        this.fk_role = fk_role;
    }

    public int getFk_role() {
        return fk_role;
    }

    public static UsuarioDTO fromEntity(UsuarioEntity entity){
        if (entity == null) return null;
        return new UsuarioDTO(
                entity.getNome(),
                entity.getEmail(),
                entity.getSenha(),
                entity.getTelefone(),
                entity.getFkRole()
        );
    }

    public UsuarioEntity toEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(this.nome);
        entity.setEmail(this.email);
        entity.setSenha(this.senha);
        entity.setTelefone(this.telefone);
        entity.setFkRole(this.fk_role);
        return entity;
    }

}
