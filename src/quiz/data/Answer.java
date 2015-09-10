package quiz.data;

public class Answer {
	private String value;
	private boolean correct;

	public Answer(String value, boolean correct) {
		this.value = value;
		this.correct = correct;
	}

	public String getValue() {
		return value;
	}

	void setValue(String value) {
		this.value = value;
	}

	public boolean isCorrect() {
		return correct;
	}

	void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	public String toString() {
		return "Answer [value=" + value + ", correct=" + correct + "]";
	}
}
