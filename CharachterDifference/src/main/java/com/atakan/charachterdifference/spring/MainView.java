package com.atakan.charachterdifference.spring;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@StyleSheet("styles/main.css")
@StyleSheet("https://fonts.googleapis.com/css?family=Montserrat&display=swap")
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private Div header;
	private Label headerTitle;
	private Button switchTextBtn;
	private Select<EditOptions> editBtn;
	private Button findDifferencesBtn;
	private Button clearTextsBtn;
	private TextArea firstTextArea;
	private TextArea secondTextArea;
	private HorizontalLayout buttonLayout;
	private HorizontalLayout textAreaLayout;
	private TextPresenter textPresenter;
	private Dialog diffDialog;

	@Autowired
	public MainView(TextPresenter textPresenter) {

		this.textPresenter = textPresenter;
		textPresenter.setView(this);

		header = new Div();
		diffDialog = new Dialog();
		headerTitle = new Label();
		headerTitle.getElement().setProperty("innerHTML", "<h1>Compare Charachters</h1>");
		editBtn = new Select<>();
		switchTextBtn = new Button("Switch Texts");
		findDifferencesBtn = new Button("Find Differences");
		clearTextsBtn = new Button("Clear Texts");
		firstTextArea = new TextArea("First Text");
		secondTextArea = new TextArea("Second Text");
		buttonLayout = new HorizontalLayout();
		textAreaLayout = new HorizontalLayout();
		buttonLayout.add(editBtn, switchTextBtn, findDifferencesBtn, clearTextsBtn);
		textAreaLayout.add(firstTextArea, secondTextArea);

		setSelect();
		setButtonListeners();
		setCssClass();

		header.add(headerTitle);
		add(header, buttonLayout, textAreaLayout);

	}

	private void setSelect() {
		editBtn.setItems(Arrays.asList(EditOptions.values()));
		editBtn.setValue(EditOptions.None);

	}

	private void setButtonListeners() {

		clearTextsBtn.addClickListener(e -> {
			textPresenter.clearAreas();
		});
		switchTextBtn.addClickListener(e -> {
			textPresenter.switchTexts(this.firstTextArea.getValue(), this.secondTextArea.getValue());
		});
		editBtn.addValueChangeListener(e -> {
			if (e.getValue() == EditOptions.Uppercase) {
				textPresenter.upperCase(firstTextArea.getValue(), secondTextArea.getValue());
			} else if (e.getValue() == EditOptions.Lowercase) {
				textPresenter.lowerCase(firstTextArea.getValue(), secondTextArea.getValue());
			} else if (e.getValue() == EditOptions.Format_Text) {
				textPresenter.formatTexts(firstTextArea.getValue(), secondTextArea.getValue());
			}
		});
		findDifferencesBtn.addClickListener(e -> {
			textPresenter.findDifferences(firstTextArea.getValue(), secondTextArea.getValue());
		});
	}

	private void setCssClass() {

		header.setClassName("header");
		headerTitle.setClassName("header-title");
		editBtn.setClassName("button");
		switchTextBtn.setClassName("button");
		findDifferencesBtn.setClassName("button");
		clearTextsBtn.setClassName("button");
		firstTextArea.setClassName("text-area");
		secondTextArea.setClassName("text-area");
		buttonLayout.setClassName("button-layout");
		textAreaLayout.setClassName("text-area-layout");
		setClassName("main");

	}

	public void clearAreas() {
		this.firstTextArea.clear();
		this.secondTextArea.clear();
	}

	public void switchTexts(String firstText, String secondText) {
		this.firstTextArea.setValue(secondText);
		this.secondTextArea.setValue(firstText);
	}

	public void upperCase(String firstTextUpperCase, String secondTextUpperCase) {
		this.firstTextArea.setValue(firstTextUpperCase);
		this.secondTextArea.setValue(secondTextUpperCase);
	}

	public void lowerCase(String firstTextLowerCase, String secondTextLowerCase) {
		this.firstTextArea.setValue(firstTextLowerCase);
		this.secondTextArea.setValue(secondTextLowerCase);
	}

	public void formatTexts(String formattedFirstText, String formattedSecondText) {
		this.firstTextArea.setValue(formattedFirstText);
		this.secondTextArea.setValue(formattedSecondText);
	}

	public void showDifferences(Html firstHTML, Html secondHTML) {
		this.diffDialog.removeAll();
		HorizontalLayout dialogLayout = new HorizontalLayout();
		diffDialog.setWidth("1200px");
		diffDialog.setHeight("700px");
		VerticalLayout firstVL = new VerticalLayout();
		VerticalLayout secondVL = new VerticalLayout();
		Label firstTitle = new Label("Differences in First Text");
		Label secondTitle = new Label("Differences in Second Text");
		dialogLayout.setSizeFull();
		Div secondArea = new Div();
		Div firstArea = new Div();
		firstVL.add(firstTitle,firstArea);
		secondVL.add(secondTitle,secondArea);
		secondArea.setClassName("div-area");
		firstArea.setClassName("div-area"); 
		Hr seperator = new Hr();
		seperator.setClassName("seperator");
		firstArea.add(firstHTML);
		secondArea.add(secondHTML);
		dialogLayout.add(firstVL,seperator,secondVL);
		diffDialog.add(dialogLayout);
		this.diffDialog.open();
	}

}
