package com.example.practicajavafx;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private ComboBox<Cliente> comboCliente;
    @FXML
    private ComboBox<String> comboProducto;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnAdd;
    @FXML
    private TableColumn<Alquiler, String> columnProducto;
    @FXML
    private TableColumn<Alquiler, String> columnFehca;

    private ObservableList<Alquiler> observableListAlquileres = FXCollections.observableArrayList();
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<Alquiler, Integer> columnNumSocio;
    @FXML
    private TableColumn<Alquiler, String> columnNombre;
    @FXML
    private TableColumn<Alquiler, String> columnApellido;
    @FXML
    private TableView<Alquiler> tablaAlquileres;
    @FXML
    private Button btnHistorial;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> productos = FXCollections.observableArrayList(
                "Regreso al Futuro", "El Ultimo Gran Heroe", "Aladdin", "Street fighters", "Cazafantasmas", "La Jungla de Cristal", "Superdetective en HollyWood");

        datePicker.setValue(LocalDate.now());
        comboProducto.setItems(productos);
        comboProducto.getSelectionModel().selectFirst();

        columnNombre.setCellValueFactory((fila) -> {
            String nombre = fila.getValue().getCliente().getNombre();
            return new SimpleStringProperty(nombre);
        });
        columnApellido.setCellValueFactory((fila) -> {
            String apellido = fila.getValue().getCliente().getApellido();
            return new SimpleStringProperty(apellido);
        });
        columnNumSocio.setCellValueFactory((fila) -> {
            Integer id = fila.getValue().getCliente().getId();
            return new SimpleObjectProperty<>(id);
        });
        columnProducto.setCellValueFactory((fila) -> {
            String fechaAlquiler = String.valueOf(fila.getValue().getFechaAlquiler(datePicker.getValue().toString()));
            return new SimpleStringProperty(fechaAlquiler);
        });
        columnFehca.setCellValueFactory((fila) -> {
            String nombreProducto = fila.getValue().getNommbreProducto();
            return new SimpleStringProperty(nombreProducto);
        });



        Cliente cliente1 = new Cliente(1, "Pepe", "Castillo");
        Cliente cliente2 = new Cliente(2, "Jose", "Lopez");
        Cliente cliente3 = new Cliente(3, "Antonio", "Martinez");
        Cliente cliente4 = new Cliente(4, "Pedro", "Jimenez");


        Alquiler alquiler1 = new Alquiler("Cazafantasmas 2", LocalDate.of(2024, 3, 8), "Todo ok", cliente1);
        Alquiler alquiler2 = new Alquiler("Star Wars: Episode IV", LocalDate.of(2024, 3, 9), "Excelente estado", cliente2);
        Alquiler alquiler3 = new Alquiler("Harry Potter and the Sorcerer's Stone", LocalDate.of(2024, 3, 10), "Recomendado", cliente3);
        Alquiler alquiler4 = new Alquiler("The Shawshank Redemption", LocalDate.of(2024, 3, 11), "Clásico", cliente4);
        Alquiler alquiler5 = new Alquiler("Inception", LocalDate.of(2024, 3, 12), "Mind-bending", cliente2);

        comboCliente.setConverter(new StringConverter<Cliente>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente.getNombre();
            }

            @Override
            public Cliente fromString(String s) {
                return null;
            }
        });

        comboCliente.getItems().setAll(cliente1,cliente2,cliente3,cliente4);
        comboCliente.getSelectionModel().selectFirst();
        tablaAlquileres.getItems().setAll(alquiler1,alquiler2,alquiler3,alquiler4,alquiler5);

    }


    @FXML
    public void addTabla(ActionEvent actionEvent) {

        Alquiler a = new Alquiler();

        a.setCliente(comboCliente.getValue());
        a.setFechaAlquiler(datePicker.getValue());
        a.setNommbreProducto(comboProducto.getValue());

        tablaAlquileres.getItems().add(a);

        tablaAlquileres.refresh();


    }

    @FXML
    public void verInforme(ActionEvent actionEvent) throws SQLException, JRException {




        Integer idCliente = 2;

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/videoclub", "root", "");

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("id", idCliente);


        var jasperPrint = JasperFillManager.fillReport("alquileres.jasper", hashMap, connection);



        JRViewer viewer = new JRViewer(jasperPrint);

        JFrame frame = new JFrame("Listado de Clientes");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);

        System.out.print("Done!");

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("alquileres.pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();

        System.out.print("Done!");

    }

    }
