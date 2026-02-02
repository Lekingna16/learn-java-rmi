package excercise4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CopyFile extends JFrame {

    private JTextField txtFrom;
    private JTextField txtTo;
    private JButton btnCopy;
    private File file;
    private File fileTo;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private JProgressBar processBar;

    public CopyFile()  {
        
        initUI ();

        initEvent();

        setVisible(true);
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void initEvent() {
        txtFrom.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(CopyFile.this);
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    file = fileChooser.getSelectedFile();
                    txtFrom.setText(file.getAbsolutePath());
                }

            }
        });
        txtTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showSaveDialog(CopyFile.this);
                if (returnVal == JFileChooser.APPROVE_OPTION){
                    fileTo = fileChooser.getSelectedFile();
                    txtTo.setText(fileTo.getAbsolutePath());
                }
            }
        });
        btnCopy.addActionListener(e -> {

            // check filePath null thi hien thi thong bao chon lai file
            if (file == null || fileTo == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn file From và To");
                return;
            }

            CopyWorker copyWorker = new CopyWorker(file, fileTo);
            copyWorker.addPropertyChangeListener(event -> {
                if ("progress".equals(event.getPropertyName())){
                    processBar.setValue((Integer) event.getNewValue());
                }
            });
            copyWorker.execute();
        });

    }

    private void initUI() {
        JLabel lblFrom = new JLabel("From");
        JLabel lblTo = new JLabel("To");
        txtFrom = new JTextField(20);
        txtTo = new JTextField(20);
        btnCopy = new JButton("Copy");
        processBar = new JProgressBar(0, 100);

        processBar.setStringPainted(true);
        JPanel pnlFrom = new JPanel();
        JPanel pnlTo = new JPanel();

        lblTo.setPreferredSize(lblFrom.getPreferredSize());

        pnlFrom.add(lblFrom);
        pnlFrom.add(txtFrom);

        pnlTo.add(lblTo);
        pnlTo.add(txtTo);

        Box box = Box.createVerticalBox();
        box.add(pnlFrom);
        box.add (Box.createVerticalStrut(5));
        box.add(pnlTo);
        box.add(Box.createVerticalStrut(5));
        box.add(btnCopy);
        box.add(Box.createVerticalStrut(5));
        box.add(processBar);

        add(box);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CopyFile :: new);
    }
}

class CopyWorker extends SwingWorker<Void, Integer> {
    private File fileFrom;
    private File fileTo;

    public CopyWorker(File fileFrom, File fileTo) {
        this.fileFrom = fileFrom;
        this.fileTo = fileTo;
    }


    @Override
    protected Void doInBackground() throws Exception {
        long totalBytes = fileFrom.length();
        long copyBytes = 0;

        try (
                InputStream in = new FileInputStream(fileFrom);
                OutputStream out = new FileOutputStream(fileTo);
                ) {
            byte[] buffer = new byte[4096];
            int byteRead;

            while ((byteRead = in.read(buffer)) != -1){
                out.write(buffer);
                copyBytes += byteRead;

                int progess = (int) (copyBytes * 100 / totalBytes);
                setProgress(progess);
                Thread.sleep(10);
            }
        }
        return null;
    }

    @Override
    protected void done() {
       JOptionPane.showMessageDialog(null, "Copy file thanh cong!");
    }
}