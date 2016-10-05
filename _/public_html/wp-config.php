<?php
/**
 * The base configuration for WordPress
 *
 * The wp-config.php creation script uses this file during the
 * installation. You don't have to use the web site, you can
 * copy this file to "wp-config.php" and fill in the values.
 *
 * This file contains the following configurations:
 *
 * * MySQL settings
 * * Secret keys
 * * Database table prefix
 * * ABSPATH
 *
 * @link https://codex.wordpress.org/Editing_wp-config.php
 *
 * @package WordPress
 */

// ** MySQL settings - You can get this info from your web host ** //
/** The name of the database for WordPress */
define('DB_NAME', 'u202732515_jybah');

/** MySQL database username */
define('DB_USER', 'u202732515_balep');

/** MySQL database password */
define('DB_PASSWORD', 'RaMarajeRy');

/** MySQL hostname */
define('DB_HOST', 'mysql');

/** Database Charset to use in creating database tables. */
define('DB_CHARSET', 'utf8');

/** The Database Collate type. Don't change this if in doubt. */
define('DB_COLLATE', '');

/**#@+
 * Authentication Unique Keys and Salts.
 *
 * Change these to different unique phrases!
 * You can generate these using the {@link https://api.wordpress.org/secret-key/1.1/salt/ WordPress.org secret-key service}
 * You can change these at any point in time to invalidate all existing cookies. This will force all users to have to log in again.
 *
 * @since 2.6.0
 */
define('AUTH_KEY',         'Hs1OBj4b2Bx95j12VKz0vG97w1oye4hdXUPWeDUwfW46eVqM3iCprttaVJmy5LLs');
define('SECURE_AUTH_KEY',  'UiEZSed2dhefSGpiqcxBBx2lg5Shzpy5rJo1pyCcKKR2tDA7769vgOquKtux9wJw');
define('LOGGED_IN_KEY',    'HnMQfDwC7CfTOPGuke7AhzAIchUnq9pnjqsnPbvGwQVhbgd9L7TkZYFt9Vx06vrc');
define('NONCE_KEY',        'V3sFUbkAr9ufVQnLfMPG6Od3fbLFi37u3aydCJAeNG8CONTmXPLHdPPzes5C6J3J');
define('AUTH_SALT',        'oQCAo5NqPLmuOYBdqLCu2Y7voK0SfIOyeFDhtUcQrb9oXAHIG0rU1TrSO9xfz433');
define('SECURE_AUTH_SALT', 'KNyiXskRr7zMeL4BFs8egIgQ2pEjyScRqTmNnlWRC7KITiLBRKhEjXYkW6jxaB6S');
define('LOGGED_IN_SALT',   'oIRyzzNAXE7FzjADaEiIqt5qQ1r0zyDmjcVfJmkMyLlyFwh63k95dcBra7jgAXl9');
define('NONCE_SALT',       'OgW5Q8f8TbeXFjxlnO33omp2gOMMsrI4oVKOadwb7GhcjIXILBPgvlxIcLjYCCkJ');

/**
 * Other customizations.
 */
define('FS_METHOD','direct');define('FS_CHMOD_DIR',0755);define('FS_CHMOD_FILE',0644);
define('WP_TEMP_DIR',dirname(__FILE__).'/wp-content/uploads');

/**
 * Turn off automatic updates since these are managed upstream.
 */
define('AUTOMATIC_UPDATER_DISABLED', true);


/**#@-*/

/**
 * WordPress Database Table prefix.
 *
 * You can have multiple installations in one database if you give each
 * a unique prefix. Only numbers, letters, and underscores please!
 */
$table_prefix  = 'niaz_';

/**
 * For developers: WordPress debugging mode.
 *
 * Change this to true to enable the display of notices during development.
 * It is strongly recommended that plugin and theme developers use WP_DEBUG
 * in their development environments.
 *
 * For information on other constants that can be used for debugging,
 * visit the Codex.
 *
 * @link https://codex.wordpress.org/Debugging_in_WordPress
 */
define('WP_DEBUG', false);

/* That's all, stop editing! Happy blogging. */

/** Absolute path to the WordPress directory. */
if ( !defined('ABSPATH') )
	define('ABSPATH', dirname(__FILE__) . '/');

/** Sets up WordPress vars and included files. */
require_once(ABSPATH . 'wp-settings.php');
