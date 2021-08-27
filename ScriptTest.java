import java.io.*;

public class ScriptTest {
	public static void main(String[] args) {
		try{

			File myObj = new File("/home/scriptTest/testScript.sh");

			BufferedWriter writer = new BufferedWriter(new FileWriter("/home/scriptTest/testScript.sh"));

			String sshKeyPath = "ssh-add <ssh key location>";
			String repoUrl = "git@github.com:username/Repo.git";
			String passphrase = "password";

			writer.write("#!/usr/bin/expect -f");
			writer.newLine();
			writer.write("spawn ssh-agent sh -c ssh-add "+sshKeyPath);
			writer.newLine();
			writer.write("spawn git clone "+repoUrl);
			writer.newLine();
			writer.write("expect \"Enter passphrase for key\\r\"");
			writer.newLine();
			writer.write("send -- "+"\""+passphrase+"\\r"+"\"");
			writer.newLine();
			writer.write("expect eof");

			writer.close();

			ProcessBuilder pb1 = new ProcessBuilder("chmod", "+x", "/home/scriptTest/testScript.sh");
			Process pr = pb1.start();
			pr.destroy();

			ProcessBuilder pb = new ProcessBuilder("/home/scriptTest/testScript.sh");
			Process process = pb.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while((line = reader.readLine())!=null){
				System.out.println(line);
			}

			int exitCode = process.waitFor();

			if(exitCode == 0 ){
				System.out.println("Cloned Successfully!!!!!");
			}else{
				System.out.println("Error>>> "+exitCode);
			}

			process.destroy();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
