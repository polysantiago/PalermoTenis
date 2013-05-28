<!-- Área de menús -->
<div id="menu-principal" class="menu" >
    <div align="center" style="display: none;" class='loader'>
        <img src='images/ajax-loader-primario.gif' alt='Loading...' />
    </div>
    <ul id="principal"></ul>

    <div id="buscador-container">
        <div id="buscador">
            <div class="buscar">
                <table border="0" cellspacing="0" cellpadding="0" >
                    <tr>
                        <td align="right" valign="top">
                            <input type="text" id="buscar-texto" size="21" value="NEWSLETTER" onFocus="this.value='';" onblur="if(!this.value) this.value='NEWSLETTER';" />
                        </td>
                        <td align="left" valign="top">
                            <img id="buscar-flecha" src="images/news-mas.jpg" />
                        </td>
                    </tr>
                </table>                            
                <div id="news-loader" style="display:none;"><img alt="Cargando..." src="/images/indicator.gif" /></div>
            </div>
            <div id="news-cartel">Ingrese su dirección de correo para suscribirse a nuestro newsletter</div>
        </div>
    </div>
</div>


<div style="display: none;" id="menu-secundario" class="menu" >
    <div align="center" style="display: none;" class='loader'>
        <img src='images/ajax-loader-secundario.gif' alt='Loading...' />
    </div>
    <ul id="secundario"></ul>
</div>

<!-- Contenidos principales -->
<div id="contenidos">
    <div align="center" style="display: none;" class='loader'>
        <img src='images/ajax-loader-contenidos.gif' alt='Loading...' />
    </div>
    <div class="central"></div>
</div>