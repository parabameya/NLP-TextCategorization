/*
author: Ameya Parab
UTD ID:2021166954
Net ID:axp132530

SUB: CS 6320 NLP HW 3
Description: this class is used to Store bigram Objects

*/

import java.util.ArrayList;
import java.util.HashMap;

public class Bigram {
	String name;
	Double positive;
	Double negative;
	Bigram(String n)
	{
		name=n;
		positive=1.0;
		negative=1.0;
	}
}