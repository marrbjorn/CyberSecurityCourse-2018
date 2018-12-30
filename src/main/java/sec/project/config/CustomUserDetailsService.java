package sec.project.config;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, String> accountDetails;

    @PostConstruct
    public void init() {
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("Valravn","(+F?%2^hXV=)2PbE@&;dJ4D]{SkZ_`Sf94n%S]uaNVP>;2w<sB?2^g");
        this.accountDetails.put("Wardruna","fh2T[_6H h9ZfWn%pFxA_UPsFA^ym2`N+{3ge_'2&ffV~dFVAP*}Q/M");
        this.accountDetails.put("Ilmarinen",";.;vmRh>Xd'5<r[5uBHgcHs3G`c7WBRe3*%Tw]y&'cDL-Qe<HkV:Z9#");
        this.accountDetails.put("Karhu","d'Y].=;msd:)fg)a<dzN{V5bj.S_fAe4&)U*/~m>Y,37>}3K'jb47^fFdds*m");
        this.accountDetails.put("Asgeir","Fylkir");
        this.accountDetails.put("Eivør","Sinklars Visa");
        this.accountDetails.put("Harkaliðið","Í Dans");
        this.accountDetails.put("Skogvidr","D%GfS_s5{@2{,U9LBc!wv7XG4U5B&8Vgr?Y;:;&4+34(>ShTY'VkS89Q");
        this.accountDetails.put("admin","admin");
        this.accountDetails.put("CMS","CMSpassword");
      //      ^ we are able to remove this "debug" password for "admin"-access;
     //      and to remove latest password which can be as "backdoor" from "tools in use";
    // but it is also good to use "encrypt"-methods for storing passwords in database;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        if (this.accountDetails.get(username).equals("admin")){
        return new org.springframework.security.core.userdetails.User(
             username,
                this.accountDetails.get(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
        }
        return new org.springframework.security.core.userdetails.User(
                username,
                this.accountDetails.get(username),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
