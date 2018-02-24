import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UnsupportedLookAndFeelException;

public class WorkNotes {

	private static final String BUTTON_SAVE_COMMAND = "Save";

	private static final String BUTTON_SAVEAS_COMMAND = "SaveAs";

	private static final String IMAGE_NAME = "icon.PNG";

	private static final String SAVE_SUCCESSFULLY_TIPS = "Save Success!";

	private String recordLocation;

	private String imageLocation;

	private JFrame frame;

	private JButton saveButton;

	private JButton saveAsButton;

	private JTextArea textArea;

	private JScrollPane scrollPane;

	private FileDialog fileDialog;

	private WorkNotes() {
		initLocation();
		initButton();
		initTextArea();
		initScrollPane();
		initFrame();
		initFileDialog();
	}

	private void initLocation() {
		this.recordLocation = WorkNotes.class.getResource("/data/").toString().replace("/", "\\").substring(6);
		this.imageLocation = WorkNotes.class.getResource("/image/").toString().replace("/", "\\").substring(6);
	}

	private void initButton() {
		this.saveButton = new JButton(WorkNotes.BUTTON_SAVE_COMMAND);
		this.saveButton.setBackground(new Color(220, 220, 220));
		this.saveButton.addActionListener(new SaveButtonListener());

		this.saveAsButton = new JButton(WorkNotes.BUTTON_SAVEAS_COMMAND);
		this.saveAsButton.setBackground(new Color(220, 220, 220));
		this.saveAsButton.addActionListener(new SaveAsButtonListerner());
	}

	private void initTextArea() {
		this.textArea = new JTextArea(18, 10);
		this.textArea.setLineWrap(true);
		this.textArea.setBackground(new Color(240, 230, 140));
	}

	private void initScrollPane() {
		this.scrollPane = new JScrollPane(this.textArea);
	}

	private void initFileDialog() {
		this.fileDialog = new FileDialog(this.frame, "SaveAs", FileDialog.SAVE);
	}

	private void initFrame() {
		this.frame = new JFrame(this.getClass().getName());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new BorderLayout());
		this.frame.add("North", this.saveButton);
		this.frame.add("South", this.saveAsButton);
		this.frame.add("Center", this.scrollPane);
		this.frame.setSize(300, 380);
		this.frame.setResizable(false);
		this.frame.setIconImage(new ImageIcon(this.imageLocation + IMAGE_NAME).getImage());
	}

	private void showFrame() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		this.frame.setVisible(true);
	}

	private void showSuccessTips() {
		JOptionPane.showMessageDialog(new JFrame(), SAVE_SUCCESSFULLY_TIPS);
	}

	private String genFileName() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
		String fileName = formatter.format(currentTime);
		return fileName;
	}

	private File genFile(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return file;
	}

	private void writeFile(String file, String text) {
		File fileObj = genFile(file);
		BufferedWriter bufferWriter = null;
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(fileObj);
			bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.append(text);
			bufferWriter.flush();
			showSuccessTips();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (bufferWriter != null) {
					bufferWriter.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	class SaveAsButtonListerner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (WorkNotes.BUTTON_SAVEAS_COMMAND.equals(e.getActionCommand())) {
				getFileDialog().setVisible(true);
				if (getFileDialog().getFile() != null) {
					writeFile(getFileDialog().getDirectory() + getFileDialog().getFile(), getTextArea().getText());
				}
			}
		}
	}

	class SaveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (WorkNotes.BUTTON_SAVE_COMMAND.equals(e.getActionCommand())) {
				writeFile(getRecordLocation() + genFileName(), getTextArea().getText());
			}
		}
	}

	public String getRecordLocation() {
		return recordLocation;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public JFrame getFrame() {
		return frame;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getSaveAsButton() {
		return saveAsButton;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public FileDialog getFileDialog() {
		return fileDialog;
	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		WorkNotes workNotes = new WorkNotes();
		workNotes.showFrame();
	}
}
