import java.util.ArrayList;

public class WordTree {
	
	private TreeNode root;
	
	public WordTree() {
		root = new TreeNode(); 
	}
	
	public int numOfWords(){
		return numOfWordsRec(root);
	}
	
	private int numOfWordsRec(TreeNode node) {
		if (node == null) {
			return 0;
		}
		int childrenWords = 0;
		for(int i = 0; i < 26; i++) {
			childrenWords += numOfWordsRec(node.children[i]);
		}
		if(node.isWord) {
			return 1 + childrenWords;
		}
		return childrenWords;
	}
	
	public void insertWord(String word) {
		insertWordRec(root, word);
	}
	
	private void insertWordRec(TreeNode node, String word) {
		char firstChar = word.charAt(0);
		if(word.length() == 1) {
			if(node.children[letterToInt(firstChar)] == null) {
				
				node.children[letterToInt(firstChar)] = new TreeNode();
				node.children[letterToInt(firstChar)].isWord = true;
			}
			else {
				node.children[letterToInt(firstChar)].isWord = true;
			}
		}
		else {
			String restOfWord = word.substring(1);
			if(node.children[letterToInt(firstChar)] == null) {
				node.children[letterToInt(firstChar)] = new TreeNode();
				insertWordRec(node.children[letterToInt(firstChar)], restOfWord);
			}
			else {
				insertWordRec(node.children[letterToInt(firstChar)], restOfWord);
			}
		}
	}
	
	public boolean contains(String word) {
		if(word.length() == 0) {
			return false;
		}
		return containswordrec(root, word);
	}
	
	private boolean containswordrec(TreeNode node, String word) {
		char firstChar = word.charAt(0);
		if(word.length() == 1) {
			if(node.children[letterToInt(firstChar)] == null) {
				return false;
			}
			if(node.children[letterToInt(firstChar)].isWord == true) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			String restOfWord = word.substring(1);
			if(node.children[letterToInt(firstChar)] != null) {
				return containswordrec(node.children[letterToInt(firstChar)],restOfWord);
			}
			else {
				return false;
			}
		}
	}
	
	public ArrayList<String> suggestCorrections(String word, int offBy){
		ArrayList<String> x = new ArrayList<String>();
		int i = 35;
		String star = new String();
		return One(root, x, star, i, word, offBy);
	}
	
	private ArrayList<String> One(TreeNode node, ArrayList<String> x, String star, int t, String prefix, int offBy){
		try {
		if (node != null && t != 35) {
			star += intToLetter(t);
		}
		if(node == null) {
			return x;
		}
		for(int i = 0; i < 26; i++) {
			One(node.children[i], x, star, i, prefix, offBy);
		}
		if(node.isWord) {
			x.add(star);
			return x;
		}
		if(x.size() == numOfWords()) {
			return two(x, prefix, offBy);
		}
		return x;
		}
		catch(Exception e) {
			return x;
		}
	}

	private ArrayList<String> two(ArrayList<String> x, String prefix, int OffBy) {
		ArrayList<String> t = new ArrayList<String>();
		int p = x.size();
		if(p > numOfWords()) {
			x = new ArrayList<String>();
			return x;
		}
		for(int i = 0; i < p; i++) {
			if(x.get(i).length() >= prefix.length()) {
				t.add(x.get(i));
			}
		}
		return filter(t, prefix, OffBy);
	}
	
	private ArrayList<String> filter(ArrayList<String> x, String prefix, int OffBy){
		ArrayList<String> t = new ArrayList<String>();
		for(int i = 0; i < x.size();i++) {
			if(x.get(i).length() == prefix.length()) {
				t.add(x.get(i));
			}
		}
		return returner(t, prefix, OffBy);
	}
	
	private ArrayList<String> returner(ArrayList<String> x, String prefix, int OffBy){
		ArrayList<String> t = new ArrayList<String>();
		for(int j = 0; j < x.size();j++) {
			int i = 0;
			int k = 0;
			for(char c: x.get(j).toCharArray()) {
				if(c == prefix.charAt(k)) {
					i++;
				}
				if(i == (prefix.length() - OffBy)) {
					t.add(x.get(j));
				}
				k++;
			}
		}
		for(int i=0;i<t.size();i++){	 
			 for(int j=i+1;j<t.size();j++){
			            if(t.get(i).equals(t.get(j))){
			                t.remove(j);
			                j--;
			            }
			    }
			 }
		return t;
	}
	
	public ArrayList<String> suggestAutoCompletes(String prefix){
		ArrayList<String> x = new ArrayList<String>();
		int i = 35;
		String star = new String();
		return AutoCheck(root, x, star, i, prefix);
	}
	
	private ArrayList<String> AutoCheck(TreeNode node, ArrayList<String> x, String star, int t, String prefix){
		try {
		if (node != null && t != 35) {
			star += intToLetter(t);
		}
		if(node == null) {
			return x;
		}
		for(int i = 0; i < 26; i++) {
			AutoCheck(node.children[i], x, star, i, prefix);
		}
		if(node.isWord) {
			x.add(star);
			return x;
		}
		if(x.size() == numOfWords()) {
			return hello(x, prefix);
		}
		return x;
		}
		catch(Exception e) {
			return x;
		}
	}

	private ArrayList<String> hello(ArrayList<String> x, String prefix) {
		ArrayList<String> t = new ArrayList<String>();
		int p = x.size();
		if(p > numOfWords()) {
			x = new ArrayList<String>();
			return x;
		}
		for(int i = 0; i < p; i++) {
			if(x.get(i).length() >= prefix.length()) {
				t.add(x.get(i));
			}
		}
		return remover(t, prefix);
	}
	
	private ArrayList<String> remover(ArrayList<String> x, String prefix){
		 ArrayList<String> trial = new  ArrayList<String>();
		for(int i = 0; i < x.size(); i++) {
			if(x.get(i).startsWith(prefix)) {
				trial.add(x.get(i));
			}
		}
		return trial;
	}
	
	public ArrayList<String> addToEach(char c, ArrayList<String> words) {
		ArrayList<String> x = new ArrayList<String>();
		for(int i = 0; i < words.size();i++) {
			String str = Character.toString(c);
			str = str.concat(words.get(i));
			x.add(str);
		}
		return x;
	}
	
	public int letterToInt(char c) {
		return c - 97;
	}
	
	public char intToLetter(int i) {
		return (char)(i + 97);
	}
}
