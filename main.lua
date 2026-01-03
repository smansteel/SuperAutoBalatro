local create_UIBox_main_menu_buttonsRef = create_UIBox_main_menu_buttons
function create_UIBox_main_menu_buttons()
    local modsButton = UIBox_button({
        id = "mods_button",
        minh = 1.25,
        minw = 3,
        col = true,
        button = "mods_button",
        colour = SMODS.mod_button_alert and (G.SETTINGS.reduced_motion and G.C.RED or SMODS.Gradients.warning_bg) or G.C.RED,
        text_colour = (SMODS.mod_button_alert and not G.SETTINGS.reduced_motion) and SMODS.Gradients.warning_text or G.C.TEXT_LIGHT,
        label = {"MULTI"},
        scale = 0.6
    })
    local menu = create_UIBox_main_menu_buttonsRef()
    table.insert(menu.nodes[1].nodes[1].nodes,2, modsButton)
    menu.nodes[1].nodes[1].config = {align = "cm", padding = 0.15, r = 0.1, emboss = 0.1, colour = G.C.L_BLACK, mid = true}
    if SMODS.mod_button_alert then 
        G.E_MANAGER:add_event(Event({
            func = function()
                if G.MAIN_MENU_UI then -- Wait until the ui is rendered before spawning the alert
                    UIBox{definition = create_UIBox_card_alert(), config = {align="tri", offset = {x = 0.05, y = -0.05}, major = G.MAIN_MENU_UI:get_UIE_by_ID('mods_button'), can_collide = false}}
                    return true
                end
            end,
            blocking = false,
            blockable = false
        }))
    end
    return menu
end

local success, dpAPI = pcall(require, "debugplus-api")


if success and dpAPI.isVersionCompatible(1) then -- Make sure DebugPlus is available and compatible
    local debugplus = dpAPI.registerID("Example")
    logger = debugplus.logger -- Provides the logger object


    debugplus.addCommand({ -- register a command
        name = "p",
        shortDesc = "Testing errors",
        desc = "This command errors, to show that DebugPlus prevents errors",
        exec = function (args, rawArgs, dp)
             print(G.STATE)
        end
    })

        debugplus.addCommand({ -- register a command
        name = "l",
        shortDesc = "Testing errors",
        desc = "This command errors, to show that DebugPlus prevents errors",
        exec = function (args, rawArgs, dp)
             print(G.STATES)
        end
    })

            debugplus.addCommand({ -- register a command
        name = "c",
        shortDesc = "Testing errors",
        desc = "This command errors, to show that DebugPlus prevents errors",
        exec = function (args, rawArgs, dp)
             print(dp)
        end
    })

end