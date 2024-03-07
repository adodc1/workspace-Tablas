package tablas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Tablas extends JPanel {
	private static final long serialVersionUID = 1L;

	public Tablas() {

		this.setLayout(new GridLayout(4, 1));

		/*
		 * Cambiar de color las celdas.
		 */
		final MiModelo modelo = new MiModelo();
		final JTable tabla = new JTable(modelo);
		tabla.setDefaultRenderer(Object.class, new MiRender());

		/*
		 * Activar MouseClick sobre la cabecera de las columnas.
		 */
		JTableHeader header = tabla.getTableHeader();
		header.addMouseListener(new TableHeaderMouseListener(tabla));

		/*
		 * Activar MouseClick sobre la tabla
		 */
		tabla.addMouseListener(new TableMouseListener(tabla));

		/*
		 * Asignar un scroll a la tabla.
		 */
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(tabla);

		/*
		 * Cargar los datos de la tabla.
		 */
		modelo.addColumn("alfa");
		modelo.addColumn("beta");
		modelo.addColumn("gamma");
		modelo.addColumn("lambda");

		for (int i = 0; i < 20; i++) {
			Object[] fila = new Object[4];
			fila[0] = "dato columna " + Math.round(Math.random() * 99);
			fila[1] = "dato columna " + Math.round(Math.random() * 99);
			fila[2] = "dato columna " + Math.round(Math.random() * 99);
			fila[3] = true;
			modelo.addRow(fila); // Añade una fila al final
		}
		modelo.setValueAt("nuevo valor", 0, 1); // Cambia el valor de la fila 1, columna 2.
		modelo.removeRow(0); // Borra la primera fila

		scroll.setPreferredSize(tabla.getPreferredScrollableViewportSize());

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(scroll, BorderLayout.CENTER);
		add(topPanel);

		Button but = new Button("Insertar");
		but.addActionListener(new ButtonInsertarListener(tabla));

		JPanel topPanel2 = new JPanel(new BorderLayout());
		topPanel2.add(but, BorderLayout.CENTER);
		add(topPanel2);

		Button but2 = new Button("Borrar");
		but2.addActionListener(new ButtonBorrarListener(tabla));

		JPanel topPanel3 = new JPanel(new BorderLayout());
		topPanel3.add(but2, BorderLayout.CENTER);
		add(topPanel3);

		Button but3 = new Button("Mover Arriba");
		but3.addActionListener(new ButtonMoverListener(tabla));

		JPanel topPanel4 = new JPanel(new BorderLayout());
		topPanel4.add(but3, BorderLayout.CENTER);
		add(topPanel4);

	}

}

class MiModelo extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	/**
	 * Declara una delda como editable.
	 */
	public boolean isCellEditable(int row, int column) {
// Aquí devolvemos true o false según queramos que una celda
// identificada por fila,columna (row,column), sea o no editable
		if (column == 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Cambiar el tipo de dato de una columna. En caso de que el dato existente no
	 * coincida con el tipo de dato que queremos poner puede generear un error.
	 */
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

class TableMouseListener extends MouseAdapter {

	private JTable table;

	public TableMouseListener(JTable table) {
		this.table = table;
	}

	public void mouseClicked(MouseEvent e) {
		int fila = table.rowAtPoint(e.getPoint());
		int columna = table.columnAtPoint(e.getPoint());

		if ((fila >= 0) && (columna >= 0)) {
			System.out.println(table.getModel().getValueAt(fila, columna));
		}
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

class ButtonInsertarListener implements ActionListener {

	private final JTable table;
	private final MiModelo modelo;

	public ButtonInsertarListener(JTable table) {
		super();
		this.table = table;
		this.modelo = (MiModelo) table.getModel();
	}

	public void actionPerformed(ActionEvent e) {
		Object[] fila = new Object[4];
		fila[0] = "dato columna " + Math.round(Math.random() * 99);
		fila[1] = "dato columna " + Math.round(Math.random() * 99);
		fila[2] = "dato columna " + Math.round(Math.random() * 99);
		fila[3] = true;
		modelo.addRow(fila); // Añade una fila al final
		table.clearSelection();
	}

}

class ButtonBorrarListener implements ActionListener {

	private final JTable table;
	private final MiModelo model;

	public ButtonBorrarListener(JTable table) {
		super();
		this.table = table;
		this.model = (MiModelo) table.getModel();
	}

	public void actionPerformed(ActionEvent e) {
		int y = this.table.getSelectedRow();
		int r = this.table.getSelectedRowCount();
		if (y >= 0 && y < this.table.getRowCount()) {
			for (int i = 0; i < r; i++) {
				this.model.removeRow(y);
			}
			table.clearSelection();
		}
	}
}

class ButtonMoverListener implements ActionListener {

	private final JTable table;
	private final MiModelo model;

	public ButtonMoverListener(JTable table) {
		super();
		this.table = table;
		this.model = (MiModelo) table.getModel();
	}

	public void actionPerformed(ActionEvent e) {
		int y = this.table.getSelectedRow();
		int r = this.table.getSelectedRowCount() - 1;
		if (y > 0 && y < this.table.getRowCount()) {
			this.model.moveRow(y, y + r, y - 1);
			this.table.setRowSelectionInterval(y - 1, y - 1 + r);
		}
	}
}
