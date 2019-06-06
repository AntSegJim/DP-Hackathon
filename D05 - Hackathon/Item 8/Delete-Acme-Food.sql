start transaction;

use `Acme-Food`;

revoke all privileges on `Acme-Food`.*from 'acme-user'@'%';

revoke all privileges on `Acme-Food`.*from 'acme-manager'@'%';

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `Acme-Food`;

commit;