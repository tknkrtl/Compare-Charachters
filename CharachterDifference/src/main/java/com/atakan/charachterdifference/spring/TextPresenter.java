package com.atakan.charachterdifference.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.textfield.TextArea;

@Service
public class TextPresenter {

	private MainView mainView;

	public TextPresenter() {

	}

	public void setView(MainView mainView) {
		this.mainView = mainView;
	}

	public void clearAreas() {
		mainView.clearAreas();
	}

	public void switchTexts(String firstText, String secondText) {
		mainView.switchTexts(firstText, secondText);
	}

	public void upperCase(String firstText, String secondText) {
		mainView.upperCase(firstText.toUpperCase(), secondText.toUpperCase());
	}

	public void lowerCase(String firstText, String secondText) {
		mainView.lowerCase(firstText.toLowerCase(), secondText.toLowerCase());
	}

	public void formatTexts(String firstText, String secondText) {

		StringBuilder firstBuilder = new StringBuilder();
		StringBuilder secondBuilder = new StringBuilder();
		int spaceCounter = 0;
		for (int i = 0; i < firstText.length(); i++) {
			if (firstText.charAt(i) != ' ') {
				firstBuilder.append((firstText.charAt(i)));
				spaceCounter = 0;
			} else if (firstText.charAt(i) == ' ' && spaceCounter < 1) {
				firstBuilder.append(firstText.charAt(i));
				spaceCounter++;
			}
		}
		for (int i = 0; i < secondText.length(); i++) {
			if (secondText.charAt(i) != ' ') {
				secondBuilder.append(secondText.charAt(i));
				spaceCounter = 0;
			} else if (secondText.charAt(i) == ' ' && spaceCounter < 1) {
				secondBuilder.append(secondText.charAt(i));
				spaceCounter++;
			}
		}
		mainView.formatTexts(firstBuilder.toString(), secondBuilder.toString());
	}

	public void findDifferences(String firstText, String secondText) {
//		<p>My mother has <span style="color:blue">blue</span> eyes.</p>
		StringBuilder f, s;
		f = new StringBuilder();
		s = new StringBuilder();

		String p = "<p>";
		String pEnd = "</p>";
		String coloredText = "<span class=\"colored-text\">";
		String coloredTextEnd = "</span>";
		boolean coloredTextActive = false;
		boolean coloredTextEndActive = false;
		Html firstHTML;
		Html secondHTML;

		f.append(p);
		s.append(p);
		if (firstText.length() >= secondText.length()) {
			for (int i = 0; i < secondText.length(); i++) {
				if (firstText.charAt(i) != secondText.charAt(i) && coloredTextActive == false) {
					coloredTextEndActive = false;
					f.append(coloredText);
					s.append(coloredText);
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
					coloredTextActive = true;
				} else if (firstText.charAt(i) != secondText.charAt(i) && coloredTextActive == true) {
					coloredTextEndActive = false;
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
				} else if (!coloredTextEndActive && coloredTextActive) {
					coloredTextActive = false;
					f.append(coloredTextEnd);
					s.append(coloredTextEnd);
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
					coloredTextEndActive = true;
				} else {
					coloredTextActive = false;
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
				}
			}
			f.append(coloredText);
			f.append(firstText.substring(secondText.length(), firstText.length()));
			f.append(coloredTextEnd);
		} else {
			for (int i = 0; i < firstText.length(); i++) {
				if (firstText.charAt(i) != secondText.charAt(i) && coloredTextActive == false) {
					coloredTextEndActive = false;
					f.append(coloredText);
					s.append(coloredText);
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
					coloredTextActive = true;
				} else if (firstText.charAt(i) != secondText.charAt(i) && coloredTextActive == true) {
					coloredTextEndActive = false;
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
				} else if (!coloredTextEndActive && coloredTextActive) {
					coloredTextActive = false;
					f.append(coloredTextEnd);
					s.append(coloredTextEnd);
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
					coloredTextEndActive = true;
				} else {
					coloredTextActive = false;
					f.append(firstText.charAt(i));
					s.append(secondText.charAt(i));
				}
			}
			s.append(coloredText);
			s.append(secondText.substring(firstText.length(), secondText.length()));
			s.append(coloredTextEnd);
		}
		f.append(pEnd);
		s.append(pEnd);
		firstHTML = new Html(f.toString());
		secondHTML = new Html(s.toString());
		mainView.showDifferences(firstHTML, secondHTML);
	}

}
