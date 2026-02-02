package excercise6;

import javax.swing.*;
import java.awt.*;
import java.io.*;


public class SplitFile extends JFrame {

    private JTextField txtInput;
    private JTextField txtOutput;
    private JTextField txtSplit;
    private JButton btnInput;
    private JButton btnOutput;
    private JButton btnSplit;
    private JProgressBar process;
    private File fileInput;
    private File folderOutput;

    public SplitFile()  {
        init ();

        initEvent ();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
    }

    private void initEvent() {
        btnInput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(SplitFile.this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                fileInput = fileChooser.getSelectedFile();
                txtInput.setText(fileInput.getAbsolutePath());
            }
        });

        btnOutput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = fileChooser.showOpenDialog(SplitFile.this);

            if (returnVal == JFileChooser.APPROVE_OPTION){
                folderOutput = fileChooser.getSelectedFile();
                txtOutput.setText(folderOutput.getAbsolutePath());
            }
        });

        btnSplit.addActionListener(e -> {
            int number = Integer.parseInt(txtSplit.getText());

            if (fileInput == null){
                JOptionPane.showMessageDialog(this, "Please choose a file!");
                return;
            }
            SplitWorker worker = new SplitWorker(fileInput, number, folderOutput);
            worker.addPropertyChangeListener(event -> {
                if ("progress".equals(event.getPropertyName())){
                    process.setValue((Integer) event.getNewValue());
                }
            });
            worker.execute();
        });
    }

    private void init() {

        JLabel lblInput = new JLabel("Input File");
        txtInput = new JTextField(20);
        JLabel lblOutput = new JLabel ("Output Folder");
        txtOutput = new JTextField (20);
        JLabel lblSplit = new JLabel ("Enter number of split to file");
        txtSplit = new JTextField(20);

        lblInput.setPreferredSize(lblSplit.getPreferredSize());
        lblOutput.setPreferredSize(lblSplit.getPreferredSize());

        btnInput = new JButton("...");
        btnOutput = new JButton("...");
        btnSplit = new JButton("Split");

        JPanel pnlInput = new JPanel();
        JPanel pnlOutput = new JPanel();
        JPanel pnlNumber = new JPanel();
        JPanel pnlButton = new JPanel();

        Box box = Box.createVerticalBox();

        pnlInput.add (lblInput);
        pnlInput.add (txtInput);
        pnlInput.add (btnInput);
        pnlOutput.add(lblOutput);
        pnlOutput.add (txtOutput);
        pnlOutput.add (btnOutput);
        pnlNumber.add (lblSplit);
        pnlNumber.add(txtSplit);
        pnlButton.add (btnSplit);

        box.add (pnlInput);
        box.add (Box.createVerticalStrut(5));
        box.add (pnlOutput);
        box.add (Box.createVerticalStrut(5));
        box.add (pnlNumber);
        box.add (Box.createVerticalStrut(5));
        box.add (pnlButton);
        box.add (Box.createVerticalStrut(5));

        process = new JProgressBar(0, 100);
        box.add (process);

        add (box);

    }

    public static void main(String[] args) {

        new SplitFile();
    }
}

class SplitWorker extends SwingWorker<Void, Integer>{
    private File file;
    private int splitNumber;
    private File folderOutput;

    public SplitWorker(File file, int splitNumber, File folderOutput) {
        this.file = file;
        this.splitNumber = splitNumber;
        this.folderOutput = folderOutput;
    }



    @Override
    protected void done() {
        JOptionPane.showMessageDialog(null, "Split completed successfully!");
    }

    @Override
    protected Void doInBackground() throws Exception {
        long totalBytes = file.length();
        long part_size = (long) totalBytes/splitNumber;
        long copiedByte = 0;
        int partNumber = 1;
        try (InputStream in = new FileInputStream(file)){
            byte[] buffer = new byte[4096];
            int byteRead = 0;

            OutputStream out = null;
            long partBytes = 0;
            while ( (byteRead = in.read(buffer)) != -1){
                if (out == null || partBytes >= part_size ){
                    if (out != null)
                        out.close();
                    out = new FileOutputStream(
                            new File(folderOutput, file.getName() + ".part" + partNumber++)
                    );
                    partBytes = 0;
                }
                out.write(buffer, 0, byteRead);
                partBytes += byteRead;
                copiedByte += byteRead;

                int percent = (int) (copiedByte * 100 / totalBytes);
                setProgress(percent);
            }
            if (out != null) out.close();

        }
        setProgress(100);
        return null;
    }
}
