local success, dpAPI = pcall(require, "debugplus-api")

function G.FUNCS.exit_lobby(e)
    G.ACTIVE_MULTI_UI = nil
    if e then
        G.FUNCS.exit_overlay_menu(e)
    end
end

function G.FUNCS.create_lobby(e)
    G.ACTIVE_LOBBY_UI = true
    if e then
        print('pouet')
    end
end

function G.FUNCS.join_lobby(e)
    G.ACTIVE_LOBBY_UI = true
    if e then
        print('prout')
    end
end


function create_UIBox_lobby_button()
    local scale = 0.75
    return (create_UIBox_generic_options({
        back_func = 'exit_lobby',
        contents = {
            {
                n = G.UIT.ROOT,
                config = {
                    padding = 0,
                    align = "cm"
                },
                nodes = {
                    {
                        n=G.UIT.C,
                        config = {
                            align = "cm",
                        },
                        nodes = {
                            {
                            n = G.UIT.R,
                            config = {
                                padding = 0.2,
                                align = "cm",
                            },
                            nodes = {
                                UIBox_button({
                                    minw = 6,
                                    button = "create_lobby",
                                    label = {"Create Lobby"}
                                })
                            }
                            },
                            {
                                n = G.UIT.R,
                                config = {
                                    padding = 0.2,
                                    align = "cm",
                                },
                                nodes = {
                                    UIBox_button({
                                        minw = 6,
                                        button = "join_lobby",
                                        label = {"Join Lobby"}
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    })
)
end


function G.FUNCS.multi_button(e)
    G.SETTINGS.paused = true
    G.ACTIVE_MULTI_UI = true
    G.FUNCS.overlay_menu({
        definition = create_UIBox_lobby_button()
    })
end



local create_UIBox_main_menu_buttonsRef = create_UIBox_main_menu_buttons
function create_UIBox_main_menu_buttons()
    local multiButton = UIBox_button({
        id = "multi_button",
        minh = 1.25,
        minw = 3,
        col = true,
        button = "multi_button",
        colour = G.C.PURPLE,
        text_colour = G.C.TEXT_LIGHT,
        label = {'MULTI'},
        scale = 0.6
    })
    local menu = create_UIBox_main_menu_buttonsRef()
    table.insert(menu.nodes[1].nodes[1].nodes, 2, multiButton)
    menu.nodes[1].nodes[1].config = {align = "cm", padding = 0.15, r = 0.1, emboss = 0.1, colour = G.C.L_BLACK, mid = true, scale = 0.45 * 1}
    return menu
end


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