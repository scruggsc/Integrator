package integrator;

import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JToggleButton;
import java.awt.Color; 
import java.io.*; 
import java.lang.*; 

import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.axis.NumberAxis; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.io.CSV;
import org.jfree.data.xy.XYDataset;

import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class MainWindow {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=-1,224
	 */
	
	/**
	 * @wbp.nonvisual location=79,324
	 */
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * change this back to private if shit breaks!!!!!!!!!!
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/resources/programIcon.png")));
		frame.setBounds(100, 100, 1600, 900);
		frame.getContentPane().setBackground(Color.darkGray);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 107, 1558, 707);
		frame.getContentPane().add(panel);
		
		JMenuItem mntmLoadSignal = new JMenuItem("Load Signal");
		mntmLoadSignal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == mntmLoadSignal){
		            final JFileChooser jFileChooser = new JFileChooser();
		            int returnVal = jFileChooser.showOpenDialog(mntmLoadSignal);
		            if(returnVal == JFileChooser.APPROVE_OPTION){
		            	
		                File file = jFileChooser.getSelectedFile();
		                XYDataset dataset = null;
		                
						try {
							dataset = GraphBuilder(file); //parse .csv
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						JFreeChart chromatogram = ChartFactory.createXYLineChart(file.getName(), "Retention Time (min)", "Signal (pA)", dataset, PlotOrientation.VERTICAL, false, true, false);
						//myChart.setBackgroundPaint(new Color(248,248,248));
						
						ChartPanel myChart = new ChartPanel(chromatogram);
						
						
						panel.setLayout(new java.awt.BorderLayout());
						panel.add(myChart,BorderLayout.CENTER);
						panel.validate();
		                
		            }else if(returnVal == JFileChooser.ERROR_OPTION){
		            	JOptionPane.showMessageDialog(null, "Error");
		            }else{
		            	JOptionPane.showMessageDialog(null, "Unknown Error");
		            }
				}
			}
			
		});
		
		
		
		mnFile.add(mntmLoadSignal);
		
		/*************************************************************************************************
		**************************************************************************************************
		*																								 *
		*** use XYSeriesCollection for overlaying signals. may be able to use for just one signal also ***
		*																								 *
		**************************************************************************************************
		**************************************************************************************************/
		
		JMenuItem mntmOverlaySignals = new JMenuItem("Overlay Signals");
		mntmOverlaySignals.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//display a message saying integrations will be deleted. give them the option of ok/cancel
				//load the second signal and delete integrations and make the integration buttons unable to be used
				//it would be really nice to be able to move the signals up and down on the y axis. not necessary right now though. 
				
				if(arg0.getSource() == mntmOverlaySignals){
					final JFileChooser jFileChooser = new JFileChooser();
					int returnVal = jFileChooser.showOpenDialog(mntmOverlaySignals);
					if(returnVal == JFileChooser.APPROVE_OPTION){
						File fileOverlay = jFileChooser.getSelectedFile();
						//parse .csv
					}else if(returnVal == JFileChooser.ERROR_OPTION){
						JOptionPane.showMessageDialog(null, "Error");
						}else{
	            	JOptionPane.showMessageDialog(null, "Unknown Error");
	            }
			}
		}
		
	});
		mnFile.add(mntmOverlaySignals);
		
		JMenuItem mntmViewIntegrationResults = new JMenuItem("View Integration Results");
		mnFile.add(mntmViewIntegrationResults);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmReportABug = new JMenuItem("Report a Bug");
		mnHelp.add(mntmReportABug);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(null);
		
		
		
		JToggleButton zoomInButton = new JToggleButton("Zoom In");
		zoomInButton.setBounds(12, 13, 80, 80);
		try {
		    zoomInButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/zoomIn.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		/*put code here to handle zooming in with the mouse
		 * zoomInButton.addItemListener(new ItemListener() {
		 
			   public void itemStateChanged(ItemEvent ev) {
			      
			   }
			});
		*/
		frame.getContentPane().add(zoomInButton);
		
		JToggleButton zoomOutButton = new JToggleButton("Zoom Out");
		zoomOutButton.setBounds(104, 13, 80, 80);
		try {
		    zoomOutButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/zoomOut.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }		
		frame.getContentPane().add(zoomOutButton);
		
		JToggleButton cursorButton = new JToggleButton("Mouse Cursor");
		cursorButton.setBounds(196, 13, 80, 80);
		try {
		    cursorButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/cursor.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(cursorButton);
		
		JToggleButton baselineButton = new JToggleButton("Draw Baseline");
		baselineButton.setBounds(288, 13, 80, 80);
		try {
		    baselineButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/baselineIcon.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(baselineButton);
		
		JToggleButton negBaselineButton = new JToggleButton("Baseline Negative Peak");
		negBaselineButton.setBounds(380, 13, 80, 80);
		try {
		    negBaselineButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/negBaselineIcon.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(negBaselineButton);
		
		JToggleButton tanSkimButton = new JToggleButton("Tangent Skim");
		tanSkimButton.setBounds(564, 13, 80, 80);
		try {
		    tanSkimButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/tangentSkimIcon.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(tanSkimButton);
		
		JToggleButton peakSplitButton = new JToggleButton("Split Peaks");
		peakSplitButton.setBounds(472, 13, 80, 80);
		try {
		    peakSplitButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/peakSplitIcon.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(peakSplitButton);
		
		JToggleButton deletePeakButton = new JToggleButton("Remove Integrated Peaks");
		deletePeakButton.setBounds(656, 13, 80, 80);
		try {
		    deletePeakButton.setIcon(new ImageIcon(MainWindow.class.getResource("/resources/deleteIntegrationIcon.png")));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }	
		frame.getContentPane().add(deletePeakButton);
		

	}


public XYDataset GraphBuilder (File file) throws IOException {
	
	final XYSeries series = new XYSeries("Chart");
	
	try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);
        NodeList nodeList = document.getElementsByTagName("sample");

        int listSize = nodeList.getLength();
        
        for( int i = 0; i < listSize; i++ )
        {
           NamedNodeMap attr = nodeList.item(i).getAttributes();
           series.add(Double.parseDouble(attr.getNamedItem("x").getNodeValue()), Double.parseDouble(attr.getNamedItem("y").getNodeValue()));
        }
   } catch (Exception e) {

   }
	
	final XYSeriesCollection dataset = new XYSeriesCollection();
	
	dataset.addSeries(series);
	
	
	return dataset;
		
	}

/* 
* Creates a chart. 
* 
* @param dataset the data for the chart. 
* 
* @return a chart. 
*/ 

/*private JFreeChart createChart(final XYDataset dataset) {
	//create the chart
	final JFreeChart chart = ChartFactory.createXYLineChart("Chromatogram", "Retention Time (min)", "Signal (pA)", dataset, PlotOrientation.VERTICAL, false, true, false);
	chart.setBackgroundPaint(new Color(247, 247, 247)); 

	// get a reference to the plot for further customization... 
	final XYPlot plot = chart.getXYPlot(); 
	plot.setBackgroundPaint(Color.white); 
	// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0)); 
	plot.setDomainGridlinePaint(Color.white); 
	plot.setRangeGridlinePaint(Color.white); 
	final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(); 
	renderer.setSeriesLinesVisible(1,true); 
	renderer.setSeriesShapesVisible(1,false); 
	plot.setRenderer(renderer); 
	//chart.setFillZoomRectangle(true);


	// change the auto tick unit selection to integer units only... 
	final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis(); 
	rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); 
	// OPTIONAL CUSTOMISATION COMPLETED. 

	return chart; 
}
*/	
	
	
}


