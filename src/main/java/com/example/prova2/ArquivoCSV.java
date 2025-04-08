package com.example.prova2;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ArquivoCSV {
    private static final String FILE_NAME = "estoque.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Carregar dados do arquivo CSV
    public static List<Medicamento> carregarMedicamentos() {
        List<Medicamento> medicamentos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");
                if (fields.length == 9) {
                    String codigo = fields[0];
                    String nome = fields[1];
                    String descricao = fields[2];
                    String principioAtivo = fields[3];
                    LocalDate dataValidade = LocalDate.parse(fields[4], DATE_FORMAT);
                    int quantidadeEstoque = Integer.parseInt(fields[5]);
                    BigDecimal preco = new BigDecimal(fields[6]);
                    boolean controlado = Boolean.parseBoolean(fields[7]);
                    Fornecedor fornecedor = new Fornecedor(fields[8], "", "", "", "", ""); // Fornecedor simples por enquanto
                    Medicamento medicamento = new Medicamento(codigo, nome, descricao, principioAtivo,
                            dataValidade, quantidadeEstoque, preco, controlado, fornecedor);
                    medicamentos.add(medicamento);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return medicamentos;
    }

    // Salvar dados no arquivo CSV
    public static void salvarMedicamentos(List<Medicamento> medicamentos) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
            for (Medicamento medicamento : medicamentos) {
                String line = String.join(";", medicamento.getCodigo(), medicamento.getNome(), medicamento.getDescricao(),
                        medicamento.getPrincipioAtivo(), medicamento.getDataValidade().toString(),
                        String.valueOf(medicamento.getQuantidadeEstoque()), medicamento.getPreco().toString(),
                        String.valueOf(medicamento.isControlado()), medicamento.getFornecedor().getCnpj());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
