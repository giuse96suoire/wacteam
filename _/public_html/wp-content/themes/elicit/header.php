<?php
/**
 * @package elicit
 */
?>
<!DOCTYPE html>
<html <?php language_attributes(); ?>>
<meta charset="<?php bloginfo( 'charset' ); ?>">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="profile" href="http://gmpg.org/xfn/11">
<link rel="pingback" href="<?php bloginfo( 'pingback_url' ); ?>">

<?php wp_head(); ?>
  </head>


	<body <?php body_class(); ?> >


<?php if ( has_nav_menu('header-menu') ): ?>
<nav class="navbar navbar-inverse">
        <div class="container-fluid">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only"><?php esc_html_e( 'Toggle navigation', 'elicit' ); ?></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
	  			<div class = "container">


                        <?php
                               
                                $headermenu = array(
                                        'theme_location' => 'header-menu',
                                        'depth'          => 2,
                                        'container'      => false,
                                        'menu_class'     => 'nav navbar-nav',
                                        'walker'         => new Bootstrap_Walker_Nav_Menu()
                                );
 
                                wp_nav_menu($headermenu);
                       
                        ?>

                           
          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
      </nav>
<?php endif; ?>
<div class='container text-center'>
<img src='https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/14224768_1754583338090799_6560402071116086890_n.jpg?oh=1475862a9b32226219e9aba8d2fa528b&oe=583B8C65' class='adver_img' style=''/>
      </div>
		</div>		

          </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->

      </nav>
	  
		<div  class = "container">