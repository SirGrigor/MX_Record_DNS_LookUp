package com.company;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.NamingException;
import java.util.Arrays;
import java.util.Comparator;

public class MailHostsLookup {
    public static void main(String args[]) {
        // explain what program does and how to use it
        if (args.length != 1) {
            System.err.println("Print out a sorted list of mail exchange servers ");
            System.err.println("    for a network domain name");
            System.err.println("USAGE: java MailHostsLookup domainName");
            System.exit(-1);
        }
        try {
            // print the sorted mail exhchange servers
            for (String mailHost: lookupMailHosts(args[0])) {
                System.out.println(mailHost);
            }
        }
        catch (NamingException e) {
            System.err.println("ERROR: No DNS record for '" + args[0] + "'");
            System.exit(-2);
        }
    }

    // returns a String array of mail exchange servers (mail hosts)
    //     sorted from most preferred to least preferred
    static String[] lookupMailHosts(String domainName) throws NamingException {
        // see: RFC 974 - Mail routing and the domain system
        // see: RFC 1034 - Domain names - concepts and facilities
        // see: http://java.sun.com/j2se/1.5.0/docs/guide/jndi/jndi-dns.html
        //    - DNS Service Provider for the Java Naming Directory Interface (JNDI)

        // get the default initial Directory Context
        InitialDirContext iDirC = new InitialDirContext();
        // get the MX records from the default DNS directory service provider
        //    NamingException thrown if no DNS record found for domainName
        Attributes attributes = iDirC.getAttributes("dns:/" + domainName, new String[] {"MX"});
        // attributeMX is an attribute ('list') of the Mail Exchange(MX) Resource Records(RR)
        Attribute attributeMX = attributes.get("MX");

        // if there are no MX RRs then default to domainName (see: RFC 974)
        if (attributeMX == null) {
            return (new String[] {domainName});
        }

        // split MX RRs into Preference Values(pvhn[0]) and Host Names(pvhn[1])
        String[][] pvhn = new String[attributeMX.size()][2];
        for (int i = 0; i < attributeMX.size(); i++) {
            pvhn[i] = ("" + attributeMX.get(i)).split("\\s+");
        }

        // sort the MX RRs by RR value (lower is preferred)
        Arrays.sort(pvhn, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2)
            {
                return (Integer.parseInt(o1[0]) - Integer.parseInt(o2[0]));
            }
        });

        // put sorted host names in an array, get rid of any trailing '.'
        String[] sortedHostNames = new String[pvhn.length];
        for (int i = 0; i < pvhn.length; i++) {
            sortedHostNames[i] = pvhn[i][1].endsWith(".") ?
                    pvhn[i][1].substring(0, pvhn[i][1].length() - 1) : pvhn[i][1];
        }
        return sortedHostNames;
    }

}