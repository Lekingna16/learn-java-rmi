package excercise5;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class OpenFile extends JFrame {

    private JMenuItem open;
    private JTextArea textArea;
    private JProgressBar progress;

    public OpenFile() {
        initUI();

        initEvent ();

        setVisible(true);
        setSize(400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void initEvent() {
        open.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(OpenFile.this);

            if (returnVal == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();

                OpenWorker openWorker = new OpenWorker(file);

                openWorker.addPropertyChangeListener(event -> {
                    if ("progress".equals(event.getPropertyName())){
                        progress.setValue((Integer) event.getNewValue());
                    }
                });
                openWorker.execute();

            }
        });
    }

    private void initUI() {
        JMenuBar mb = new JMenuBar();

        textArea = new JTextArea();

        JMenu file = new JMenu("File");
        JMenuItem new_file = new JMenuItem("Tap tin moi");
         open = new JMenuItem("Mo tap tin");
        JMenuItem save = new JMenuItem("Luu tap tin");
        JMenuItem print = new JMenuItem("In ra may in");
        JMenuItem exit = new JMenuItem("Thoat");

        file.add(new_file);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");


        mb.add(file);
        mb.add(edit);
        mb.add(help);

        setJMenuBar(mb);

        add (textArea, BorderLayout.CENTER);

        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        add(progress, BorderLayout.SOUTH);


    }

   public static void main(String[] args) {
        SwingUtilities.invokeLater(OpenFile::new);
    }
    class OpenWorker extends SwingWorker<Void, String> {

        private File file;

        public OpenWorker(File file) {
            this.file = file;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String line : chunks){
                textArea.append(line);
            }

        }

        @Override
        protected void done() {
            setProgress(100);
            JOptionPane.showMessageDialog(null, "Open file thanh cong!");
        }

        @Override
        protected Void doInBackground() throws Exception {

            try (
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(new FileInputStream(file)))
                    ){
                int totalBytes = (int) file.length();
                int byteReaded = 0;
                String line = "";
                while ((line = br.readLine()) != null) {
                    publish(line + "\n");
                    byteReaded += line.getBytes().length;

                    int progress = byteReaded * 100 / totalBytes;
                    setProgress(progress);
                }
            }

            return null;
        }
    }
}
