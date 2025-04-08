package com.example.prova2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends Application {

    private TableView<Medicamento> tabelaMedicamentos;
    private ObservableList<Medicamento> medicamentosData;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        medicamentosData = FXCollections.observableArrayList(ArquivoCSV.carregarMedicamentos());

        tabelaMedicamentos = new TableView<>();
        tabelaMedicamentos.setItems(medicamentosData);

        TableColumn<Medicamento, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        TableColumn<Medicamento, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        TableColumn<Medicamento, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(cellData -> cellData.getValue().descricaoProperty());

        tabelaMedicamentos.getColumns().addAll(colCodigo, colNome, colDescricao);

        TextField tfCodigo = new TextField();
        tfCodigo.setPromptText("Código (7 caracteres)");

        TextField tfNome = new TextField();
        tfNome.setPromptText("Nome");

        TextField tfDescricao = new TextField();
        tfDescricao.setPromptText("Descrição");

        TextField tfPrincipioAtivo = new TextField();
        tfPrincipioAtivo.setPromptText("Princípio Ativo");

        DatePicker dpValidade = new DatePicker();
        dpValidade.setPromptText("Data de Validade");

        TextField tfQuantidadeEstoque = new TextField();
        tfQuantidadeEstoque.setPromptText("Quantidade Estoque");

        TextField tfPreco = new TextField();
        tfPreco.setPromptText("Preço");

        CheckBox cbControlado = new CheckBox("Controlado");

        TextField tfCnpjFornecedor = new TextField();
        tfCnpjFornecedor.setPromptText("CNPJ do Fornecedor");

        Button btnCadastrar = new Button("Cadastrar Medicamento");
        btnCadastrar.setOnAction(e -> {
            try {
                Fornecedor fornecedor = new Fornecedor(tfCnpjFornecedor.getText(), "", "", "", "", "");
                Medicamento medicamento = new Medicamento(
                        tfCodigo.getText(),
                        tfNome.getText(),
                        tfDescricao.getText(),
                        tfPrincipioAtivo.getText(),
                        dpValidade.getValue(),
                        Integer.parseInt(tfQuantidadeEstoque.getText()),
                        new BigDecimal(tfPreco.getText()),
                        cbControlado.isSelected(),
                        fornecedor
                );
                medicamentosData.add(medicamento);
                ArquivoCSV.salvarMedicamentos(medicamentosData); // Salva no arquivo
                clearForm(tfCodigo, tfNome, tfDescricao, tfPrincipioAtivo, dpValidade, tfQuantidadeEstoque, tfPreco, cbControlado, tfCnpjFornecedor);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Por favor, preencha todos os campos corretamente.");
            }
        });

        Button btnExcluir = new Button("Excluir Medicamento");
        btnExcluir.setOnAction(e -> {
            Medicamento selectedMedicamento = tabelaMedicamentos.getSelectionModel().getSelectedItem();
            if (selectedMedicamento != null) {
                medicamentosData.remove(selectedMedicamento);
                ArquivoCSV.salvarMedicamentos(medicamentosData); // Salva no arquivo após remoção
            } else {
                showAlert(Alert.AlertType.WARNING, "Seleção Inválida", "Selecione um medicamento para excluir.");
            }
        });

        Button btnRelatorioEstoqueBaixo = new Button("Estoque Baixo (< 5)");
        btnRelatorioEstoqueBaixo.setOnAction(e -> {
            var medicamentosBaixo = Relatorios.medicamentosEstoqueBaixo(medicamentosData);
            showMedicamentosList(medicamentosBaixo);
        });

        Button btnRelatorioVencimento = new Button("Próximos ao Vencimento");
        btnRelatorioVencimento.setOnAction(e -> {
            var medicamentosVencendo = Relatorios.medicamentosProximosVencimento(medicamentosData);
            showMedicamentosList(medicamentosVencendo);
        });

        VBox formLayout = new VBox(10, tfCodigo, tfNome, tfDescricao, tfPrincipioAtivo, dpValidade, tfQuantidadeEstoque, tfPreco, cbControlado, tfCnpjFornecedor, btnCadastrar);
        formLayout.setPadding(new Insets(10));

        VBox buttonLayout = new VBox(10, btnExcluir, btnRelatorioEstoqueBaixo, btnRelatorioVencimento);
        buttonLayout.setPadding(new Insets(10));

        HBox rootLayout = new HBox(20, formLayout, buttonLayout);
        rootLayout.setPadding(new Insets(20));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(new Label("Gestão de Estoque de Medicamentos"));
        mainLayout.setCenter(tabelaMedicamentos);
        mainLayout.setBottom(rootLayout);

        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setTitle("Sistema de Farmácia");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearForm(TextField tfCodigo, TextField tfNome, TextField tfDescricao, TextField tfPrincipioAtivo,
                           DatePicker dpValidade, TextField tfQuantidadeEstoque, TextField tfPreco, CheckBox cbControlado, TextField tfCnpjFornecedor) {
        tfCodigo.clear();
        tfNome.clear();
        tfDescricao.clear();
        tfPrincipioAtivo.clear();
        dpValidade.setValue(null);
        tfQuantidadeEstoque.clear();
        tfPreco.clear();
        cbControlado.setSelected(false);
        tfCnpjFornecedor.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showMedicamentosList(Iterable<Medicamento> medicamentos) {
        List<Medicamento> medicamentoList = new ArrayList<>();
        medicamentos.forEach(medicamentoList::add);

        TableView<Medicamento> tempTable = new TableView<>();
        tempTable.setItems(FXCollections.observableArrayList(medicamentoList));

        TableColumn<Medicamento, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
        TableColumn<Medicamento, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());

        tempTable.getColumns().addAll(colCodigo, colNome);

        Stage stage = new Stage();
        stage.setTitle("Lista de Medicamentos");
        stage.setScene(new Scene(tempTable, 400, 300));
        stage.show();
    }

}
