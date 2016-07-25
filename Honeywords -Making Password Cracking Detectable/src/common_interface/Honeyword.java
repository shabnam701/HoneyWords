package common_interface;

import java.awt.List;
import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import database.JDBC;

public class Honeyword {

	public static String characters[]  ={"a","s","d","f","g","h","j","k","l","q","w","e","r","t","y","u","i","o","p","z","x","c","v","b","n","m"};
	static Set<String> honeyWordSet = new HashSet<String>();
	public static void generateHoneywords()
	{
		Random rn = new Random();
		StringBuffer word = null;
		while(honeyWordSet.size()<101)
		{
			word = new StringBuffer("");
			int numOfChar = 0;
			do
			{
				numOfChar = rn.nextInt(10);
			}while(numOfChar<1);

			for(int j=0;j<numOfChar;j++)
			{
				word.append(characters[rn.nextInt(characters.length)]);
			}
			honeyWordSet.add(word.toString());

		}
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("jsonFiles\\honeyWord.json"),
					new TypeReference<Map<String, Object>>() {
					});
			for(Object obj : honeyWordSet)
			{
				map.put(obj.toString(), "");
			}
			mapper.writeValue(new File("jsonFiles\\honeyWord.json"), map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void ResetPassword(String user)
	{
		String newPassword = JOptionPane.showInputDialog("Enter new Password");
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("jsonFiles/"+user+".json"),
					new TypeReference<Map<String, Object>>() {
					});
			map.clear();
			mapper.writeValue(new File("jsonFiles/"+user+".json"), map);
			Honeyword.generatePotentialPassword(newPassword,user);
			JDBC.updateIP(user);
			JOptionPane.showMessageDialog(null, "Password updated sucessfully");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void ClearHoneywords()
	{
		try
		{
			for(File file : new File("jsonFiles").listFiles())
			{
				ObjectMapper mapper = new ObjectMapper();
				Map<String, Object> map = mapper.readValue(
						file,
						new TypeReference<Map<String, Object>>() {
						});
				map.clear();
				mapper.writeValue(file, map);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void generatePotentialPassword(String password,String uname) {
		// TODO Auto-generated method stub
		Map<String, Object> honeywords = null;
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			honeywords = mapper.readValue(
					new File("jsonFiles\\honeyWord.json"),
					new TypeReference<Map<String, Object>>() {
					});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Set honeywordSet = honeywords.keySet();
		LinkedList honeywordList = new LinkedList();
		for(Object obj : honeywordSet)
			honeywordList.add(obj.toString());
		Map potentialPassword = new HashMap();
		int index = new Random().nextInt(Honeychecker.k);
		potentialPassword.put("index", index);
		Map Passwords = new HashMap<>();
		Passwords.put(index, hashing(password));
		for(int i=1;i<Honeychecker.k;i++)
		{
			if(i!=index)
			{
				Passwords.put(i, hashing(honeywordList.get(new Random().nextInt(honeywordList.size())).toString()));
			}
		}
		potentialPassword.put("password", Passwords);
		File file = new File("jsonFiles/"+uname+".json");
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
				PrintWriter out = new PrintWriter(file);
				out.print("{}");
				out.close();
			}
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					file,
					new TypeReference<Map<String, Object>>() {
					});
			mapper.writeValue(file, potentialPassword);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String hashing(String data)
	{
		String hash = null;
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes("UTF-8"));
			BigInteger c = new BigInteger(1,md.digest());
			hash = c.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hash;
	}

	public static boolean checkPasswordIsPotentialPassword(String pass,String user) {

		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("jsonFiles\\"+user+".json"),
					new TypeReference<Map<String, Object>>() {
					});
			Map potentialPass = (Map)map.get("password");
			if(potentialPass.containsValue(pass))
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isPasswordOrHoneyword(String passwordConvertedToHash,String user) {
		// TODO Auto-generated method stub
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> map = mapper.readValue(
					new File("jsonFiles\\"+user+".json"),
					new TypeReference<Map<String, Object>>() {
					});
			Map potentialPass = (Map)map.get("password");
			int index = Integer.parseInt(map.get("index").toString());
			String PasswordInJson = potentialPass.get(index+"").toString();
			if(PasswordInJson.equals(passwordConvertedToHash))
				return true;
			else
				return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static String findHoneyUsingBruteForce(String user) {
		// TODO Auto-generated method stub
		String foundPassword = "";
		try
		{
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> userMap = mapper.readValue(
					new File("jsonFiles\\"+user+".json"),
					new TypeReference<Map<String, Object>>() {
					});
			Map potentialPassword = (Map)userMap.get("password");
			Map<String, Object> honeyword = mapper.readValue(
					new File("jsonFiles\\honeyWord.json"),
					new TypeReference<Map<String, Object>>() {
					});
			Set honeywordsSet = honeyword.keySet();
			for(Object ky : honeywordsSet)
			{
				String hashValue = hashing(ky.toString());
				if(potentialPassword.containsValue(hashValue))
				{
					foundPassword = ky.toString();
					break;
				}
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return foundPassword;
	}

}
