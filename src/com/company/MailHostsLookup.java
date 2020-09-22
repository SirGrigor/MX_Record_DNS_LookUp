package com.company;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Comparator;

public class MailHostsLookup {
    public static void main(String args[]) {
        if (args.length != 1) {
            System.err.println("Print out a sorted list of mail exchange servers ");
            System.err.println("    for a network domain name");
            System.err.println("USAGE: java MailHostsLookup domainName");
            System.exit(-1);
        }
        try {
            for (String mailHost: lookupMailHosts(args[0])) {
                System.out.println(mailHost);
            }
        }
        catch (NamingException e) {
            System.err.println("ERROR: No DNS record for '" + args[0] + "'");
            System.exit(-2);
        }
    }

    static String[] lookupMailHosts(String domainName) throws NamingException {
        InitialDirContext iDirC = new InitialDirContext();
        Attributes attributes = iDirC.getAttributes("dns:/" + domainName, new String[] {"MX"});
        Attribute attributeMX = attributes.get("MX");

        if (attributeMX == null) {
            return (new String[] {domainName});
        }

        String[][] pvhn = new String[attributeMX.size()][2];
        for (int i = 0; i < attributeMX.size(); i++) {
            pvhn[i] = ("" + attributeMX.get(i)).split("\\s+");
        }

        Arrays.sort(pvhn, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2)
            {
                return (Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]));
            }
        });

        String[] sortedHostNames = new String[pvhn.length];
        for (int i = 0; i < pvhn.length; i++) {
            sortedHostNames[i] = pvhn[i][1].endsWith(".") ?
                    pvhn[i][1].substring(0, pvhn[i][1].length() - 1) : pvhn[i][1];
        }
        return sortedHostNames;
    }
}