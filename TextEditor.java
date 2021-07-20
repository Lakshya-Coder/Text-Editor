import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

	// Object
	JTextArea textArea;
	JScrollPane scrollPain;
	JLabel fontLabel;
	JSpinner fontSize;
	JButton fontColorButton;
	JComboBox fontBox;

	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;

	// Variable
	private final int WIDTH = 1280;
	private final int HIGHT = 660;

	// Constructor
	TextEditor() {
		// Frame
		this.setDefaultCloseOperation(3);
		this.setTitle("Text Editor");
		this.setSize(WIDTH, HIGHT);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);

		// Object of textArea
		textArea = new JTextArea();

		// Setting of textArea
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));

		// Object of scrollPain
		scrollPain = new JScrollPane(textArea);

		// Setting of scrollPain
		scrollPain.setPreferredSize(new Dimension(WIDTH - 50, HIGHT - 50));
		scrollPain.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// Object of fontSize
		fontSize = new JSpinner();

		fontLabel = new JLabel("Font: ");

		// Setting of fontSize
		fontSize.setPreferredSize(new Dimension(50, 25));
		fontSize.setValue(20);
		fontSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSize.getValue()));
			}
		});

		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);

		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");

		// --------menu bar---------------//

		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");

		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		// Add all the item to fileMenu
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);

		// Add file menu to menuBar
		menuBar.add(fileMenu);

		// --------menu bar---------------//
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSize);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPain);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == fontColorButton) {
			JColorChooser colorChooser = new JColorChooser();

			Color color = colorChooser.showDialog(null, "Chose a color", Color.black);

			textArea.setForeground(color);
		}

		if (e.getSource() == fontBox) {
			textArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}

		if (e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("C:\\Users\\Lakshya Seth\\Desktop"));

			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
			fileChooser.setFileFilter(filter);

			int response = fileChooser.showOpenDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {

				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

				Scanner sc = null;

				try {
					sc = new Scanner(file);
					if (file.isFile()) {
						while (sc.hasNextLine()) {
							String n = sc.nextLine() + "\n";
							textArea.append(n);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				finally {
					sc.close();
				}
			}
		}
		if (e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("C:\\Users\\Lakshya Seth\\Desktop>"));

			int response = fileChooser.showSaveDialog(null);

			if (response == JFileChooser.APPROVE_OPTION) {

				File file;
				PrintWriter fileOut = null;

				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				finally {
					fileOut.close();
				}
			}
		}

		if (e.getSource() == exitItem) {
			System.exit(0);
		}

	}

}
