package f06ModalReader.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
//import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
//import javafx.scene.control.SelectionMode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import f06ModalReader.Main;
import f06ModalReader.model.Data;
import f06ModalReader.model.NXReader;

public class TableViewController {
    // Reference to the main application.
    private Main mainApp;
    // The data as an observable list
	private ObservableList<Data> modalData = FXCollections.observableArrayList();
	// NXReader
	private NXReader f06file;
	// chosen *.f06-file --> file.chooser
	private File f06;
	private String f06parent;
	// filechooser
	private FileChooser fileChooser;
	/**
	 * FXML - Scene Builder
	 */
    @FXML
    private TableView<Data> modalTable;
    @FXML
    private TableColumn<Data, Integer> modeColumn;
    @FXML
    private TableColumn<Data, Double> frequencyColumn;
    @FXML
    private TableColumn<Data, Double> xColumn;
    @FXML
    private TableColumn<Data, Double> yColumn;
    @FXML
    private TableColumn<Data, Double> zColumn;
    @FXML
    private TableColumn<Data, Double> rxColumn;
    @FXML
    private TableColumn<Data, Double> ryColumn;
    @FXML
    private TableColumn<Data, Double> rzColumn;
    @FXML
    private Label errorLabel;
    @FXML
    private Label fileLabel;
    /**
     * Called when the user clicks on buttons
     */
    @FXML
    private void handleOpenFile() {
    	// init state - first open filechooser
    	if( f06==null && f06parent==null ) {
    		fileChooser = new FileChooser();
    		fileChooser.setTitle("Open NXNastran *.f06-File");
    		// Set extension filter
            FileChooser.ExtensionFilter extFilter = 
                    new FileChooser.ExtensionFilter("f06 files (*.f06)", "*.f06", "*.F06");
            fileChooser.getExtensionFilters().add(extFilter);
            // open file with filechooser
    		if( (f06 = fileChooser.showOpenDialog(mainApp.getPrimaryStage()))!=null ) {
    			// save directory
    			f06parent = f06.getParent();
    	    	// display file
    	    	fileLabel.setText("File: " + f06);
    	    	// open file and read modal table
    	    	f06file = new NXReader(f06);
    	    	// update modalTable
    	    	updateModalTable(f06file);
    		}
    		else {
    			fileLabel.setText("File: NO file loaded...");
    			errorLabel.setText("ERROR: No file loaded! Open *.f06 NXNastran file...");
    		}
    	}
    	// reopen filechosser
    	else {
    		// set parent directory
    		fileChooser.setInitialDirectory(new File(f06parent));
    		// open file with filechooser
    		if( (f06 = fileChooser.showOpenDialog(mainApp.getPrimaryStage()))!=null ) {
    			// save directory
    			f06parent = f06.getParent();
    	    	// display file
    	    	fileLabel.setText("File: " + f06);
    	    	// open file and read modal table
    	    	f06file = new NXReader(f06);
    	    	// update modalTable
    	    	updateModalTable(f06file);
    		}
    		else {
    			fileLabel.setText("File: NO file loaded...");
    			errorLabel.setText("ERROR: No file loaded! Open *.f06 NXNastran file...");
    		}
    	}
    }
    @FXML
    private void handleUpdate() {
    	if( f06!=null ) {
    		f06file = new NXReader(f06); // open file and read modal table
    		// update modalTable
    		updateModalTable(f06file);
    	} else {
    		fileLabel.setText("File: NO file loaded...");
    		errorLabel.setText("ERROR: No file loaded! Open *.f06 NXNastran file...");
    	}
    }
    @FXML
    private void handleClear() {
    	modalData.clear();
    	// update modalTable
    	modalTable.setItems(modalData);
    	// clear error
    	errorLabel.setText("");
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public TableViewController() { }
    
    /** 
     * update Table with new *f06-file
     */
    private void updateModalTable( NXReader f06file ) {
    	modalData.clear();
    	try {
	    	for( int i=0; i<f06file.getNumberOfModes(); i++ ) {
		        modalData.add(new Data( f06file.getModes()[i],f06file.getF()[i], 
		        		f06file.getX()[i], f06file.getY()[i], f06file.getZ()[i],
		        		f06file.getRX()[i], f06file.getRY()[i], f06file.getRZ()[i]));
	    	}
	    	// update modalTable
	    	modalTable.setItems(modalData);
	    	// clear error
	    	errorLabel.setText("");
    	} catch( NullPointerException e ) {
    		errorLabel.setText("ERROR: NO modal table in *.f06-file found!");
    	}
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// init labels
    	errorLabel.setText("");
    	fileLabel.setText("File: NO file loaded...");
        // Initialize the data table with the columns.
        modeColumn.setCellValueFactory(cellData -> cellData.getValue().modeProperty().asObject());
        frequencyColumn.setCellValueFactory(cellData -> cellData.getValue().frequencyProperty().asObject());
        xColumn.setCellValueFactory(cellData -> cellData.getValue().xProperty().asObject());
        yColumn.setCellValueFactory(cellData -> cellData.getValue().yProperty().asObject());
        zColumn.setCellValueFactory(cellData -> cellData.getValue().zProperty().asObject());
        rxColumn.setCellValueFactory(cellData -> cellData.getValue().rxProperty().asObject());
        ryColumn.setCellValueFactory(cellData -> cellData.getValue().ryProperty().asObject());
        //rzColumn.setCellValueFactory(cellData -> Bindings.format("%.2f%%", cellData.getValue().rzProperty().multiply(100.0)));
        rzColumn.setCellValueFactory(cellData -> cellData.getValue().rzProperty().asObject());
        
        // Column format (with local classes)
        frequencyColumn.setCellFactory(column -> { return new colDesignF<Data, Double>(); });
        xColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
        yColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
        zColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
        rxColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
        ryColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
        rzColumn.setCellFactory(column -> { return new colDesign<Data, Double>(); });
    	// selection 
    	//modalTable.getSelectionModel().setCellSelectionEnabled(true);
    	//modalTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    
    @FXML
    void rightclickOnTable() {
    	// create Context-Menu
    	final ContextMenu cm = new ContextMenu();
    	cm.setAutoHide(true);
    	// add Copy - entry
    	MenuItem cmItem1 = new MenuItem("Copy Full Table...");
    	// if Item - Copy is clicked...action
    	cmItem1.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent e) {
    	    	//System.out.println(xColumn.getCellData(11));
    	    	StringBuilder clipboardString = new StringBuilder();
    	    	//TODO: second copy-for selection
    	    	try {
	    	    	for( int i=0; i<f06file.getNumberOfModes(); i++ ) {
	    	    		clipboardString.append(f06file.getRow(i));
	    	    	}
    	    	} 
    	    	catch( NullPointerException e1 ) {
    	    		errorLabel.setText("ERROR: No file loaded! Open *.f06 NXNastran file...");
    	    	}
    	    	// create clipbard-object and copy "clipboardString"
    	        final ClipboardContent content = new ClipboardContent();
    	        //System.out.println(clipboardString);
    	        content.putString(clipboardString.toString());
    	        Clipboard.getSystemClipboard().setContent(content);
    	    }
    	});
    	cm.getItems().add(cmItem1); // add item to context-menu
    	// right-mouse-click --> show context-menu at mouse-position
    	modalTable.addEventHandler(MouseEvent.MOUSE_CLICKED,
    	    new EventHandler<MouseEvent>() {
    	        @Override public void handle(MouseEvent e) {
    	            if (e.getButton() == MouseButton.SECONDARY)  
    	                cm.show(modalTable, e.getScreenX(), e.getScreenY());
    	            else if( e.getButton() == MouseButton.PRIMARY)
    	            	cm.hide();
    	        }
    	});
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        //modalTable.setItems(mainApp.getModalData());
    }
    
    /***************************** COLUMN FORMAT ******************************************
     * Local Class for column format - X,Y,Z,Rx,Ry,Rz --> "%"
     *
     * @param <S> Model Data
     * @param <T> Data Type of Column
     */
    class colDesign<S,T extends Double> extends TableCell<S,T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                // Format to two digits and multiply with 100 to get %
                setText( String.format("%.2f%%", item*100.0 ) );

                // Style all dates >5%
                if (item>0.05) {
                    setTextFill(Color.BLACK);
                    setStyle("-fx-background-color: lightgreen");
                } else {
                    setTextFill(Color.BLACK);
                    setStyle("");
                }
            }
        }
    }
    /**
     * Local Class for column format - f --> "2 decimal places"
     *
     * @param <S> Model Data
     * @param <T> Data Type of Column
     */
    class colDesignF<S,T extends Double> extends TableCell<S,T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                // Format to two digits and multiply with 100 to get %
                setText( String.format("%.1f", item ) );
                setStyle("-fx-font-weight: bold");
            }
        }
    }
}