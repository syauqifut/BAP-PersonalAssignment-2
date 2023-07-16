package CRUD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Puskesmas extends JFrame {
    private List<Pasien> pasiens;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField, nikField, birthDateField, addressField;

    JLabel nameLabel = new JLabel("Nama Pasien:");
    JLabel nikLabel = new JLabel("NIK:");
    JLabel birthDateLabel = new JLabel("Tanggal Lahir (YYYY-MM-DD):");
    JLabel addressLabel = new JLabel("Alamat:");

    JButton addButton = new JButton("Tambah");
    JButton updateButton = new JButton("Update");
    JButton deleteButton = new JButton("Hapus");

    public Puskesmas() {
        setTitle("Puskesmas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pasiens = new ArrayList<>();

        // Tabel pasien
        String[] columnNames = {"Nama Pasien", "NIK", "Tanggal Lahir", "Alamat"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        // Form input data pasien
        nameField = new JTextField(20);
        nikField = new JTextField(15);
        birthDateField = new JTextField(10);
        addressField = new JTextField(50);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String nik = nikField.getText();
                LocalDate birthDate = LocalDate.parse(birthDateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                String address = addressField.getText();

                if (isNikExist(nik)) {
                    showError("NIK sudah ada!");
                } else {
                    Pasien patient = new Pasien(name, nik, birthDate, address);
                    pasiens.add(patient);

                    Object[] rowData = {patient.getName(), patient.getNik(), patient.getBirthDate(), patient.getAddress()};
                    tableModel.addRow(rowData);

                    clearFields();
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    Pasien selectedPatient = pasiens.get(selectedRow);
                    String name = nameField.getText();
                    String nik = nikField.getText();
                    LocalDate birthDate = LocalDate.parse(birthDateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                    String address = addressField.getText();

                    if (!selectedPatient.getNik().equals(nik) && isNikExist(nik)) {
                        showError("NIK sudah ada!");
                    } else {
                        selectedPatient.setName(name);
                        selectedPatient.setNik(nik);
                        selectedPatient.setBirthDate(birthDate);
                        selectedPatient.setAddress(address);

                        tableModel.setValueAt(selectedPatient.getName(), selectedRow, 0);
                        tableModel.setValueAt(selectedPatient.getNik(), selectedRow, 1);
                        tableModel.setValueAt(selectedPatient.getBirthDate(), selectedRow, 2);
                        tableModel.setValueAt(selectedPatient.getAddress(), selectedRow, 3);

                        clearFields();
                    }
                } else {
                    showError("Pilih pasien yang akan diupdate!");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    pasiens.remove(selectedRow);
                    tableModel.removeRow(selectedRow);

                    clearFields();
                } else {
                    showError("Pilih pasien yang akan dihapus!");
                }
            }
        });

        // Main layout
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(nikLabel);
        inputPanel.add(nikField);
        inputPanel.add(birthDateLabel);
        inputPanel.add(birthDateField);
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean isNikExist(String nik) {
        for (Pasien patient : pasiens) {
            if (patient.getNik().equals(nik)) {
                return true;
            }
        }
        return false;
    }

    private void clearFields() {
        nameField.setText("");
        nikField.setText("");
        birthDateField.setText("");
        addressField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Puskesmas();
            }
        });
    }
}
