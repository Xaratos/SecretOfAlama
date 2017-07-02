package de.secretofalama.editors;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import de.secretofalama.objects.datatypes.Date;
import de.secretofalama.objects.datatypes.Geschlecht;
import de.secretofalama.objects.gameelements.Charachter;

public class CharachterEditor extends EditorPart {

	private FileEditorInput fileEditorInput;
	private Charachter charachter;

	@Override
	public void createPartControl(Composite parent) {
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
		try {

			File file = fileEditorInput.getPath().toFile();
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

}
