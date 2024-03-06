package tablas;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Tablas extends JPanel {
	private static final long serialVersionUID = 1L;

	public Tablas() {

		setLayout(new GridLayout(1, 3));

		final MiModelo modelo = new MiModelo();
		final JTable tabla = new JTable(modelo);
		tabla.setDefaultRenderer(Object.class, new MiRender());

		JTableHeader header = tabla.getTableHeader();
		header.addMouseListener(new TableHeaderMouseListener(tabla));

		tabla.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				int fila = tabla.rowAtPoint(e.getPoint());
				int columna = tabla.columnAtPoint(e.getPoint());

				if ((fila >= 0) && (columna >= 0)) {
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

		for (int i = 0; i < 20; i++) {
			Object[] fila = new Object[4];
			fila[0] = "dato columna 1";
			fila[1] = "dato columna 2";
			fila[2] = "dato columna 3";
			fila[3] = true;
			modelo.addRow(fila); // Añade una fila al final
			modelo.setValueAt("nuevo valor", 0, 1); // Cambia el valor de la fila 1, columna 2.
		}
		modelo.removeRow(0); // Borra la primera fila

		scroll.setPreferredSize(tabla.getPreferredScrollableViewportSize());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(scroll, BorderLayout.CENTER);
		add(topPanel);
	}

}

class MiModelo extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public boolean isCellEditable(int row, int column) {
// Aquí devolvemos true o false según queramos que una celda
// identificada por fila,columna (row,column), sea o no editable
		if (column == 2) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {

		if (columnIndex == 3) {
			return Boolean.class;
		}
		return super.getColumnClass(columnIndex);
	}
}

class MiRender extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (row == 10 && !isSelected) {
			this.setOpaque(true);
			this.setBackground(Color.LIGHT_GRAY);
			this.setForeground(Color.BLACK);

		} else if (isSelected) {
			this.setOpaque(true);
			this.setBackground(Color.BLUE);
			this.setForeground(Color.WHITE);

		} else {
			// Restaurar los valores por defecto
			this.setOpaque(true);
			this.setBackground(Color.WHITE);
			this.setForeground(Color.BLACK);
		}

		return this;
	}
}

class TableHeaderMouseListener extends MouseAdapter {

	private JTable table;

	public TableHeaderMouseListener(JTable table) {
		this.table = table;
	}

	public void mouseClicked(MouseEvent event) {
		int columna = table.columnAtPoint(event.getPoint());
		Object obj = table.getColumnModel().getColumn(columna).getHeaderValue();
		System.out.println("Column header #" + columna + " is clicked");
		System.out.println(obj.toString());
	}
}
