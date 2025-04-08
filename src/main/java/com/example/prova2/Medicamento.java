package com.example.prova2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Medicamento {
    private String codigo;
    private String nome;
    private String descricao;
    private String principioAtivo;
    private LocalDate dataValidade;
    private int quantidadeEstoque;
    private BigDecimal preco;
    private boolean controlado;
    private Fornecedor fornecedor;

    public Medicamento(String codigo, String nome, String descricao, String principioAtivo,
                       LocalDate dataValidade, int quantidadeEstoque, BigDecimal preco, boolean controlado, Fornecedor fornecedor) {
        if (!codigo.matches("[a-zA-Z0-9]{7}")) {
            throw new IllegalArgumentException("Código deve ter 7 caracteres alfanuméricos.");
        }
        if (nome == null || nome.trim().isEmpty() || nome.length() < 3) {
            throw new IllegalArgumentException("Nome não pode ser vazio e deve ter no mínimo 3 caracteres.");
        }
        if (dataValidade.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Data de validade não pode ser no passado.");
        }
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa.");
        }
        if (preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser um valor positivo.");
        }
        if (fornecedor == null) {
            throw new IllegalArgumentException("Fornecedor não pode ser nulo.");
        }

        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.principioAtivo = principioAtivo;
        this.dataValidade = dataValidade;
        this.quantidadeEstoque = quantidadeEstoque;
        this.preco = preco;
        this.controlado = controlado;
        this.fornecedor = fornecedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public boolean isControlado() {
        return controlado;
    }

    public void setControlado(boolean controlado) {
        this.controlado = controlado;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Medicamento [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao + ", principioAtivo="
                + principioAtivo + ", dataValidade=" + dataValidade + ", quantidadeEstoque=" + quantidadeEstoque
                + ", preco=" + preco + ", controlado=" + controlado + ", fornecedor=" + fornecedor + "]";
    }

    public StringProperty codigoProperty() {
        return new SimpleStringProperty(codigo);
    }

    public StringProperty nomeProperty() {
        return new SimpleStringProperty(nome);
    }

    public StringProperty descricaoProperty() {
        return new SimpleStringProperty(descricao);
    }

}