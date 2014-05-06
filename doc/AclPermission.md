ACL Permissions
===============
Die (A)ccess (C)ontrol (L)ist ist in der Klasse ```de.haw.mps.MpsAcl``` untergebracht.

Permissions
-----------

<table>
	<tr>
		<th>Action</th>
		<th>Notiz</th>
		<th>USER_ASSEMBLY</th>
		<th>USER_CALLCENTERAGENT</th>
	</tr>
	<tr>
		<th colspan="4">AssemblyOrder Actions</th>
	</tr>
	<tr>
		<td>ADD_ASSEMBLYORDER</td>
		<td></td>
		<td>FALSE</td>
		<td>TRUE</td>	
	</tr>
	<tr>
		<td>UPDATE_STATUS_ASSEMBLYORDER</td>
		<td></td>
		<td>TRUE</td>
		<td>TRUE</td>	
	</tr>
	<tr>
		<th colspan="4">Element Actions</th>
	</tr>
	<tr>
		<td>ADD_ELEMENT</td>
		<td></td>
		<td>TRUE</td>
		<td>FALSE</td>	
	</tr>
	<tr>
		<td>UPDATE_ELEMENT</td>
		<td></td>
		<td>TRUE</td>
		<td>FALSE</td>	
	</tr>
	<tr>
		<td>REMOVE_ELEMENT</td>
		<td></td>
		<td>TRUE</td>
		<td>FALSE</td>	
	</tr>
</table>