package iCenter.View;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import iCenter.Controller.dataRegisterController;

public class ICenterView{


     private JFrame frame = new JFrame();
     
     public ICenterView() {

        JPanel mainContenedor = new JPanel();
        mainContenedor.setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel();
        JPanel panelSur= new JPanel();
        JPanel panelCentral = new JPanel();
        JPanel panelCentral2 = new JPanel();

        panelNorte.setLayout(new BorderLayout());
        panelSur.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panelCentral.setLayout(new BorderLayout());
        panelCentral2.setLayout(new BorderLayout());

        JButton registrar = new JButton("Registrar data");
        JButton generar = new JButton("Generar Reporte");
        JButton salir = new JButton("Salir");

        panelSur.add(registrar);
        panelSur.add(generar);
        panelSur.add(salir);

        JPanel jpTitulo = new JPanel();
        jpTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel titulo = new JLabel("Ingrese data del equipo:");
        jpTitulo.add(titulo);

        panelNorte.add(jpTitulo);

        JPanel jpDescripcion = new JPanel();
        jpDescripcion.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlDescripcion = new JLabel("Descripción:");
        JTextField jtDescripcion = new JTextField(30);

        JPanel jpCant = new JPanel();
        jpCant.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlCant = new JLabel("Cantidad:");
        JTextField jtCant = new JTextField(5);

        jpCant.add(jlCant);
        jpCant.add(jtCant);
        
        JPanel jpCosto = new JPanel();
        jpCosto.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlCosto = new JLabel("Costo Unitario (Bs.):");
        JTextField jtCosto = new JTextField(10);

        jpCosto.add(jlCosto);
        jpCosto.add(jtCosto);
        
        jpDescripcion.add(jlDescripcion);
        jpDescripcion.add(jtDescripcion);

        JPanel jpCostoCant = new JPanel();
        jpCostoCant.setLayout(new BorderLayout());
        jpCostoCant.add(jpCosto, BorderLayout.EAST);
        jpCostoCant.add(jpCant, BorderLayout.WEST);

        JPanel jpFecha = new JPanel();
        jpFecha.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlFecha = new JLabel("<html>Fecha de adquisición:<br> dd/mm/aaaa</html>");
        JTextField jtFecha = new JTextField(8);

        jpFecha.add(jlFecha);
        jpFecha.add(jtFecha);

        JPanel jpFactura = new JPanel();
        jpFactura.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel jlFactura = new JLabel("Nro. de Factura:");
        JTextField jtFactura = new JTextField(10);

        jpFactura.add(jlFactura);
        jpFactura.add(jtFactura);

        JPanel jpFechaFactura = new JPanel();
        jpFechaFactura.setLayout(new BorderLayout());
        jpFechaFactura.add(jpFactura, BorderLayout.EAST);
        jpFechaFactura.add(jpFecha, BorderLayout.WEST);

        JPanel jpCedula= new JPanel();
        jpCedula.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel jlCedula = new JLabel("C.I. del Responsable del Equipo:");
        JTextField jtCedula = new JTextField(10);

        jpCedula.add(jlCedula);
        jpCedula.add(jtCedula);

        JPanel relleno = new JPanel();

        panelCentral.add(panelCentral2, BorderLayout.CENTER);
        panelCentral2.add(jpCostoCant, BorderLayout.NORTH);
        panelCentral2.add(jpFechaFactura, BorderLayout.CENTER);
        panelCentral2.add(jpCedula, BorderLayout.SOUTH);
        panelCentral.add(relleno, BorderLayout.SOUTH);

        panelCentral.add(jpDescripcion, BorderLayout.NORTH);
        

        mainContenedor.add(panelNorte, BorderLayout.NORTH);
        mainContenedor.add(panelCentral, BorderLayout.CENTER);
        mainContenedor.add(panelSur, BorderLayout.SOUTH);


        panelSur.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelNorte.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        jpTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jpDescripcion.setBorder(BorderFactory.createEmptyBorder(0,0, 0,0));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(0,15,0,15));
        panelCentral2.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        relleno.setBorder(BorderFactory.createEmptyBorder(0,0, 20,0));
       


        // Lógica Botones

        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{

                // Captando valores de entrada del formulario de Registro ICenter. 
                String desc_valor = jtDescripcion.getText();
                int cantidad_equipos = Integer.parseInt(jtCant.getText());
                BigDecimal costo_unitario = new BigDecimal(jtCosto.getText().replace(",", "."));
                String fecha = jtFecha.getText();
                String num_factura = jtFactura.getText();
                String cedula = jtCedula.getText();

                // Envío de datos captados al controlador para crear nuevo registro. 
                dataRegisterController.createRegistro(cedula, cantidad_equipos, costo_unitario, desc_valor, fecha, num_factura);
               

            } catch(InputMismatchException ex){
                ex.printStackTrace();
                
            }
            }
        });

        generar.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new IReportView();
            }
        });

         salir.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        frame.setTitle("Registro y Control de Equipos en Centro de Investigación");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Salir de la aplicación
        frame.setResizable(false);
        frame.setSize(490, 320);
        frame.add(mainContenedor);
    }

    public void displayIcenterView(boolean visible) {
        frame.setVisible(visible);
    }
}