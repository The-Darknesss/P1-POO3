package com.example.prova2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Relatorios {

    public static List<Medicamento> medicamentosProximosVencimento(List<Medicamento> medicamentos) {
        LocalDate hoje = LocalDate.now();
        return medicamentos.stream()
                .filter(m -> m.getDataValidade().isBefore(hoje.plusDays(30)) && m.getDataValidade().isAfter(hoje))
                .collect(Collectors.toList());
    }

    public static List<Medicamento> medicamentosEstoqueBaixo(List<Medicamento> medicamentos) {
        return medicamentos.stream()
                .filter(m -> m.getQuantidadeEstoque() < 5)
                .collect(Collectors.toList());
    }

    public static Map<Fornecedor, BigDecimal> valorTotalEstoquePorFornecedor(List<Medicamento> medicamentos) {
        return medicamentos.stream()
                .collect(Collectors.groupingBy(Medicamento::getFornecedor,
                        Collectors.reducing(BigDecimal.ZERO, m -> m.getPreco().multiply(BigDecimal.valueOf(m.getQuantidadeEstoque())), BigDecimal::add)));
    }

    public static Map<Boolean, List<Medicamento>> medicamentosControlados(List<Medicamento> medicamentos) {
        return medicamentos.stream()
                .collect(Collectors.partitioningBy(Medicamento::isControlado));
    }
}
