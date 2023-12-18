package iCenter.View;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import iCenter.Controller.generateReportController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IReportView {

    public IReportView(){

        generateReportController reportController = new generateReportController();

        JFrame frame = new JFrame();
        frame.setSize(450, 320);
        frame.setTitle("Reporte del Inventario del Centro de Investigación");
        frame.setResizable(false);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BorderLayout());

        JPanel North1 = new JPanel();
        North1.setLayout(new BorderLayout());
        North1.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel componenteTOP = new JPanel();
        JPanel componenteTOP_SOUTH = new JPanel();
        
        JLabel tipo_label = new JLabel("Tipo reporte:");
        JRadioButton opcion_individual = new JRadioButton("Individual");
        JRadioButton opcion_general = new JRadioButton("General");
        ButtonGroup radio_group = new ButtonGroup();
        radio_group.add(opcion_individual);
        radio_group.add(opcion_general);
        componenteTOP.setLayout(new FlowLayout(FlowLayout.LEFT));
        componenteTOP.add(tipo_label);
        componenteTOP.add(opcion_individual);
        componenteTOP.add(opcion_general);

        JLabel cedula_label = new JLabel("C.I. del Responsable de equipos:");
        JTextField cedula_field = new JTextField(8);
        JButton totalizar = new JButton("Totalizar");
        componenteTOP_SOUTH.setLayout(new FlowLayout(FlowLayout.LEFT));
        componenteTOP_SOUTH.add(cedula_label);
        componenteTOP_SOUTH.add(cedula_field);
        componenteTOP_SOUTH.add(totalizar);

        North1.add(componenteTOP, BorderLayout.NORTH);
        North1.add(componenteTOP_SOUTH, BorderLayout.SOUTH);


        JPanel CENTER1 = new JPanel();
        CENTER1.setLayout(new BorderLayout());
        CENTER1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel equipos_value = new JLabel("      ");
        equipos_value.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JLabel bolivares_value = new JLabel( "              ");
        bolivares_value.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

        JLabel total_label = new JLabel("Totalización:");
        JLabel equipos_label = new JLabel (" Equipos.");
        JLabel bs_label = new JLabel (" Bs.");

        JPanel equipos = new JPanel();
        equipos.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        equipos.setLayout(new FlowLayout(FlowLayout.LEFT));
        equipos.add(equipos_value);
        equipos.add(equipos_label);

        JPanel bolivares = new JPanel();
        bolivares.setLayout(new FlowLayout(FlowLayout.LEFT));
        bolivares.add(bolivares_value);
        bolivares.add(bs_label);

        CENTER1.add(total_label, BorderLayout.NORTH);
        CENTER1.add(equipos, BorderLayout.CENTER);
        CENTER1.add(bolivares, BorderLayout.SOUTH);

        JPanel South1 = new JPanel();
        JButton continuar = new JButton("Continuar");
        South1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        South1.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 20));
        South1.add(continuar);
       
        contenedor.add(South1, BorderLayout.SOUTH);
        contenedor.add(North1, BorderLayout.NORTH);
        contenedor.add(CENTER1, BorderLayout.WEST);

        CENTER1.setVisible(false);
        componenteTOP_SOUTH.setVisible(false);
        

        JPanel panel_general = new JPanel();
        String[][] registros = reportController.generateGeneralReport();
        String[] columnas = {"C.I. Responsable", "Cantidad equipos", "Monto total (Bs.)"};
        DefaultTableModel modelo = new DefaultTableModel(registros, columnas);
        JTable table = new JTable(modelo);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel p_tabla = new JPanel(new BorderLayout());
        Dimension dimension = new Dimension(400, 120); 
        p_tabla.setPreferredSize(dimension);
        p_tabla.add(scrollPane);
        panel_general.add(p_tabla);
        
        //panel_general.add(CENTER1);
        panel_general.setVisible(false);


        contenedor.add(panel_general, BorderLayout.CENTER);
        
        totalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = cedula_field.getText();
                if (cedula.isEmpty()){
                    equipos_value.setText("      ");
                    bolivares_value.setText("              ");
                    JOptionPane.showMessageDialog(null, "El campo cédula no puede estar vacío.");
                } else{
                    equipos_value.setText("      ");
                    bolivares_value.setText("              ");
                    Object[] totalizaciones = reportController.generateIndividualReport(cedula);
                    if(totalizaciones == null){
                        JOptionPane.showMessageDialog(null, "No se hallaron registros para la cédula: " + cedula);
                        cedula_field.setText("");

                    }else{
                        equipos_value.setText(totalizaciones[0]+ "");
                        bolivares_value.setText(totalizaciones[1]+ "");
                    }
                    
                }    
    }
});
        opcion_individual.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                componenteTOP_SOUTH.setVisible(true);
                CENTER1.setVisible(true);
                panel_general.setVisible(false);
                
}
});      opcion_general.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                componenteTOP_SOUTH.setVisible(false);
                CENTER1.setVisible(false);
                panel_general.setVisible(true);           
}
});
        frame.add(contenedor);
        frame.setVisible(true);
    }
}
