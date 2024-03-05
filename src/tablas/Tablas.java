package tablas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Tablas extends JPanel {
	private static final long serialVersionUID = 1L;

	public Tablas() {

		setLayout(new GridLayout(1, 3));

		final MiModelo modelo = new MiModelo();
		final JTable tabla = new JTable(modelo);

		tabla.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if ((fila > -1) && (columna > -1)) {
					System.out.println(modelo.getValueAt(fila, columna));
				}
			}

		});

		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(tabla);

		modelo.addColumn("alfa");
		modelo.addColumn("beta");
		modelo.addColumn("gamma");
		modelo.addColumn("lambda");

		Object[] fila = new Object[4];
		fila[0] = "dato columna 1";
		fila[1] = "dato columna 2";
		fila[2] = "dato columna 3";
		fila[3] = "dato columna 4";

		modelo.addRow(fila); // Añade una fila al final
		modelo.setValueAt("nuevo valor", 0, 1); // Cambia el valor de la fila 1, columna 2.
//		modelo.removeRow(0); // Borra la primera fila

		scroll.setPreferredSize(tabla.getPreferredScrollableViewportSize());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(scroll, BorderLayout.CENTER);
		add(topPanel);
	}

}

class MiModelo extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Primera columna Boolean, segunda Integer y el resto Object
	 * 
	 * @return
	 */
	public Class<?> getColumnClass(int columna) {
		if (columna == 0)
			return (Class<?>) Boolean.class;
		if (columna == 1)
			return Integer.class;
		return Object.class;
	}

	public boolean isCellEditable(int row, int column) {
// Aquí devolvemos true o false según queramos que una celda
// identificada por fila,columna (row,column), sea o no editable
		if (column == 3) {
			return true;
		} else {
			return false;
		}
	}
}