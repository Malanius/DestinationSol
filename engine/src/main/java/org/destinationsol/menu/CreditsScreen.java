/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.destinationsol.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.destinationsol.Const;
import org.destinationsol.GameOptions;
import org.destinationsol.SolApplication;
import org.destinationsol.assets.Assets;
import org.destinationsol.common.SolColor;
import org.destinationsol.common.SolMath;
import org.destinationsol.ui.DisplayDimensions;
import org.destinationsol.ui.FontSize;
import org.destinationsol.ui.SolInputManager;
import org.destinationsol.ui.SolUiBaseScreen;
import org.destinationsol.ui.SolUiControl;
import org.destinationsol.ui.UiDrawer;

import java.util.ArrayList;

public class CreditsScreen extends SolUiBaseScreen {
    private static final float MAX_AWAIT = 6f;
    private final TextureAtlas.AtlasRegion backgroundTexture;
    private final SolUiControl closeControl;

    private DisplayDimensions displayDimensions;

    private final ArrayList<String> myPages = new ArrayList<>();
    private final Color myColor;
    private int pageIndex;
    private float pageProgressPercent;

    CreditsScreen(GameOptions gameOptions) {
        displayDimensions = SolApplication.displayDimensions;

        closeControl = new SolUiControl(MenuLayout.bottomRightFloatingButton(displayDimensions), true, gameOptions.getKeyEscape());
        closeControl.setDisplayName("Close");
        controls.add(closeControl);
        myColor = SolColor.col(1, 1);

        String[][] sss = {
                {
                        "A game from",
                        "",
                        "MovingBlocks"
                },
                {
                        "Original Creators",
                        "",
                        "Idea, coding, team lead:",
                        "Milosh Petrov",
                        "",
                        "Drawing:",
                        "Kent C. Jensen",
                        "",
                        "Additional coding:",
                        "Nika \"NoiseDoll\" Burimenko",
                        "",
                        "Additional drawing:",
                        "Julia Nikolaeva"
                },
                {
                        "Contributors on GitHub",
                        "",
                        "Cervator, Rulasmur",
                        "theotherjay, LinusVanElswijk",
                        "SimonC4, grauerkoala, rzats",
                        "LadySerenaKitty, askneller",
                        "JGelfand, AvaLanCS, scirelli",
                        "Sigma-One, vampcat, malanius",
                        "AonoZan, ererbe, SurajDutta",
                        "jasyohuang, Steampunkery",
                        "Graviton48, Adrijaned, MaxBorsch",
                        "sohil123, FieryPheonix909",
                        "digitalripperynr, NicholasBatesNZ",
                        "Pendi, Torpedo99, AndyTechGuy",
                        "BenjaminAmos, dannykelemen",
                        "msteiger, oniatus, arpitkamboj",
                        "manas96, IsaacLic, Mpcs, ZPendi",
                        "nailorcngci, FearlessTobi, ujjman",
                        "ThisIsPIRI"
                },
                {
                        "Soundtrack by NeonInsect"
                },
                {
                        "Game engine:",
                        "LibGDX",
                        "",
                        "Windows wrapper:",
                        "Launch4J"
                },
                {
                        "Font:",
                        "\"Jet Set\" by Captain Falcon",
                        "",
                        "Sounds by FreeSound.org users:",
                        "Smokum, Mattpavone",
                        "Hanstimm, Sonidor,",
                        "Isaac200000, TheHadnot, Garzul",
                        "Dpoggioli, Raremess, Giddykipper,",
                        "Steveygos93",
                },
        };
        for (String[] ss : sss) {
            StringBuilder page = new StringBuilder();
            for (String s : ss) {
                page.append(s).append("\n");
            }
            myPages.add(page.toString());
        }

        backgroundTexture = Assets.getAtlasRegion("engine:mainMenuBg", Texture.TextureFilter.Linear);
    }

    @Override
    public void onAdd(SolApplication solApplication) {
        pageIndex = 0;
        pageProgressPercent = 0;
        myColor.a = 0;
    }

    @Override
    public void updateCustom(SolApplication solApplication, SolInputManager.InputPointer[] inputPointers, boolean clickedOutside) {
        if (closeControl.isJustOff()) {
            solApplication.getInputManager().setScreen(solApplication, solApplication.getMenuScreens().main);
            return;
        }
        pageProgressPercent += Const.REAL_TIME_STEP / MAX_AWAIT;
        if (pageProgressPercent > 1) {
            pageProgressPercent = 0;
            pageIndex++;
            if (pageIndex >= myPages.size()) {
                pageIndex = 0;
            }
        }
        float a = pageProgressPercent * 2;
        if (a > 1) {
            a = 2 - a;
        }
        a *= 3;
        myColor.a = SolMath.clamp(a);

        solApplication.getMenuBackgroundManager().update();
    }

    @Override
    public void drawBackground(UiDrawer uiDrawer, SolApplication solApplication) {
        uiDrawer.draw(backgroundTexture, displayDimensions.getRatio(), 1, displayDimensions.getRatio() / 2, 0.5f, displayDimensions.getRatio() / 2, 0.5f, 0, SolColor.WHITE);
        solApplication.getMenuBackgroundManager().draw(uiDrawer);
    }

    @Override
    public void drawText(UiDrawer uiDrawer, SolApplication solApplication) {
        uiDrawer.drawString(myPages.get(pageIndex), displayDimensions.getRatio() / 2, .5f, FontSize.MENU, true, myColor);
    }
}
