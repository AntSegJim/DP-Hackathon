
package forms;

public class PositionFinderForm {

	private String	keyWord;


	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public PositionFinderForm create() {
		final PositionFinderForm form = new PositionFinderForm();

		form.setKeyWord("");

		return form;
	}

}
