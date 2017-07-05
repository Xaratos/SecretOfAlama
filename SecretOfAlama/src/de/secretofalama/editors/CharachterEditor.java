package de.secretofalama.editors;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import de.secretofalama.objects.datatypes.Geschlecht;
import de.secretofalama.objects.gameelements.Charachter;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;

public class CharachterEditor extends EditorPart {
	private DataBindingContext m_bindingContext;
	public CharachterEditor() {
	}

	private FileEditorInput fileEditorInput;
	private Charachter charachter;
	private Text txtNachname;
	private Text txtVorname;
	private String charImage;
	private ComboViewer comboViewerGeschlecht;

	@Override
	public void createPartControl(Composite parent) {

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);

		TabItem tbtmGrundinformationen = new TabItem(tabFolder, SWT.NONE);
		tbtmGrundinformationen.setText("Grundinformationen");

		Group grpBasis = new Group(tabFolder, SWT.NONE);
		grpBasis.setText("Basis");
		tbtmGrundinformationen.setControl(grpBasis);
		grpBasis.setLayout(new GridLayout(3, false));

		Label lblNachname = new Label(grpBasis, SWT.NONE);
		lblNachname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNachname.setText("Nachname");

		txtNachname = new Text(grpBasis, SWT.BORDER);
		txtNachname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label charPicture = new Label(grpBasis, SWT.NONE);
		if (charImage != null) {
			charPicture.setImage(new Image(parent.getDisplay(), charImage));
		}
		charPicture.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 2));

		Label lblVorname = new Label(grpBasis, SWT.NONE);
		lblVorname.setText("Vorname");

		txtVorname = new Text(grpBasis, SWT.BORDER);
		txtVorname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblGeschlecht = new Label(grpBasis, SWT.NONE);
		lblGeschlecht.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGeschlecht.setText("Geschlecht");
		
		comboViewerGeschlecht = new ComboViewer(grpBasis, SWT.NONE);
		comboViewerGeschlecht.setContentProvider(ArrayContentProvider.getInstance());
		comboViewerGeschlecht.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if(element instanceof Geschlecht) {
					return ((Geschlecht) element).getAnzeigetext();
				}
				return super.getText(element);
			}
		});
		comboViewerGeschlecht.setInput(Geschlecht.values());
		Combo comboGeschlecht = comboViewerGeschlecht.getCombo();
		comboGeschlecht.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(grpBasis, SWT.NONE);
		
		TabItem tbtmControls = new TabItem(tabFolder, SWT.NONE);
		tbtmControls.setText("Controls");
		
		Button btnSave = new Button(tabFolder, SWT.NONE);
		tbtmControls.setControl(btnSave);
		btnSave.setText("Save");
		btnSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doSave(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
			
		});
		m_bindingContext = initDataBindings();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		File file = fileEditorInput.getPath().toFile();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Charachter.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(charachter, file);
			jaxbMarshaller.marshal(charachter, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof FileEditorInput)) {
			throw new RuntimeException("Muss File Editor Input sein");
		}
		setSite(site);
		setInput(input);
		this.fileEditorInput = (FileEditorInput) input;
		File file = fileEditorInput.getPath().toFile();
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(Charachter.class);
			if (file.length() != 0) {
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				this.charachter = (Charachter) jaxbUnmarshaller.unmarshal(file);
			} else {
				this.charachter = new Charachter();
				doSave(null);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		if (fileEditorInput.getPath().removeFileExtension().addFileExtension("png").toFile().exists()) {
			this.charImage = fileEditorInput.getPath().removeFileExtension().addFileExtension("png").toPortableString();
		}

	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {

	}

	@Override
	public String getPartName() {
		return "CE - " + charachter.getVorname() + " " + charachter.getNachname();
	}
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		IObservableValue observeTextTxtNachnameObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtNachname);
		IObservableValue nachnameCharachterObserveValue = PojoProperties.value("nachname").observe(charachter);
		bindingContext.bindValue(observeTextTxtNachnameObserveWidget, nachnameCharachterObserveValue, null, null);
		//
		IObservableValue observeTextTxtVornameObserveWidget = WidgetProperties.text(SWT.Modify).observe(txtVorname);
		IObservableValue vornameCharachterObserveValue = PojoProperties.value("vorname").observe(charachter);
		bindingContext.bindValue(observeTextTxtVornameObserveWidget, vornameCharachterObserveValue, null, null);
		
		IViewerObservableValue selectedTodo = ViewerProperties.singleSelection().observe(comboViewerGeschlecht);
		IObservableValue geschlechtCharachterObserveValue = PojoProperties.value("geschlecht").observe(charachter);
		bindingContext.bindValue(selectedTodo, geschlechtCharachterObserveValue, null, null);
		
		//
		return bindingContext;
	}
}
