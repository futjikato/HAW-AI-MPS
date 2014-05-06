package de.haw.mps;

import sun.security.acl.AclEntryImpl;
import sun.security.acl.AclImpl;
import sun.security.acl.PermissionImpl;
import sun.security.acl.PrincipalImpl;

import java.security.acl.NotOwnerException;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category de.haw.mps
 */
public class MpsAcl {

    public static final PermissionImpl ADD_ASSEMBLYORDER = new PermissionImpl("mps.assemblyorder.add");
    public static final PermissionImpl ADD_ELEMENT = new PermissionImpl("mps.element.add");

    public static final PrincipalImpl USER_ADMIN  = new PrincipalImpl("mps.user.admin");
    public static final PrincipalImpl USER_ASSEMBLY  = new PrincipalImpl("mps.user.assembly");
    public static final PrincipalImpl USER_CALLCENTERAGENT  = new PrincipalImpl("mps.user.callcenteragent");

    private AclImpl acl;

    private MpsAcl() throws NotOwnerException {
        // declare admin principal
        acl = new AclImpl(USER_ADMIN, "MPS ACL");

        initPrincipals();

        // assembly user permissions
        addAssemblyEntries();
        // call center agent permissions
        addCallCenterEntries();
    }

    private void initPrincipals() throws NotOwnerException {
        acl.addOwner(USER_ADMIN, USER_ASSEMBLY);
        acl.addOwner(USER_ADMIN, USER_CALLCENTERAGENT);
    }

    private void addAssemblyEntries() throws NotOwnerException {
        // positive
        AclEntryImpl entry = new AclEntryImpl(USER_ASSEMBLY);
        entry.addPermission(ADD_ELEMENT);
        acl.addEntry(USER_ADMIN, entry);

        // negative
        entry = new AclEntryImpl(USER_ASSEMBLY);
        entry.setNegativePermissions();
        entry.addPermission(ADD_ASSEMBLYORDER);
        acl.addEntry(USER_ADMIN, entry);
    }

    private void addCallCenterEntries() throws NotOwnerException {
        // positive
        AclEntryImpl entry = new AclEntryImpl(USER_CALLCENTERAGENT);
        entry.addPermission(ADD_ASSEMBLYORDER);
        acl.addEntry(USER_ADMIN, entry);

        // negative
        entry = new AclEntryImpl(USER_CALLCENTERAGENT);
        entry.setNegativePermissions();
        entry.addPermission(ADD_ELEMENT);
        acl.addEntry(USER_ADMIN, entry);
    }
}
